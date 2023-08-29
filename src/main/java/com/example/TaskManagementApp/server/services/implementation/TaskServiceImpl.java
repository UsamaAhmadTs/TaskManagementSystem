package com.example.TaskManagementApp.server.services.implementation;


import com.example.TaskManagementApp.server.dao.*;
import com.example.TaskManagementApp.server.dto.TaskDto;
import com.example.TaskManagementApp.server.dto.UserDto;
import com.example.TaskManagementApp.server.entities.Employee;
import com.example.TaskManagementApp.server.entities.Manager;
import com.example.TaskManagementApp.server.entities.Task;
import com.example.TaskManagementApp.server.entities.User;
import com.example.TaskManagementApp.server.exception.BadRequestException;
import com.example.TaskManagementApp.server.exception.ForbiddenAccessException;
import com.example.TaskManagementApp.server.exception.UserNotFoundException;
import com.example.TaskManagementApp.server.services.EmployeeService;
import com.example.TaskManagementApp.server.services.ManagerService;
import com.example.TaskManagementApp.server.services.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TaskServiceImpl implements TaskService {

    private final EmployeeService employeeService;
    private final ManagerService managerServiceImpl;
    private final TaskRepo taskRepo;
    private final UserRepo userRepo;
    private final EmployeeRepo employeeRepo;
    private final ManagerRepo managerRepo;
    //private final TaskHistoryRepo taskHistoryRepo;
    private static TaskServiceImpl instance;
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    public TaskServiceImpl(EmployeeService employeeService,UserRepo userRepo, ManagerService managerService, TaskRepo taskRepo, EmployeeRepo employeeRepo, ManagerRepo managerRepo) {
        this.employeeService = employeeService;
        this.managerServiceImpl = managerService;
        this.taskRepo = taskRepo;
        this.employeeRepo = employeeRepo;
        this.managerRepo = managerRepo;
       // this.taskHistoryRepo = taskHistoryRepo;

        this.userRepo= userRepo;

    }
    @Override
    public List<Task> viewAllTasksByStatus(Task.Status status){
        List<Task> tasksByStatus = new ArrayList<>();
        for (Task task : taskRepo.findAll()) {
            if (task.getTaskStatus() == status) {
                tasksByStatus.add(task);
            }
        }
        return tasksByStatus;
    }
    @Override
    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

       @Override
       public void UpdateStatus(Task task, Task.Status status, UserDto authenticatedUser) {
           if (status == Task.Status.COMPLETED) {
               validateManager(authenticatedUser);
               changeTaskStatusToCompleted(task);
           } else if (status == Task.Status.IN_PROGRESS) {
               validateEmployee(authenticatedUser);
               changeTaskStatusToInProgress(task);
           } else if(status == Task.Status.IN_REVIEW) {
               validateEmployee(authenticatedUser);
               changeTaskStatusToInReview(task);
           }
       }

    public void changeTaskStatusToInProgress(Task task) {
        validateTaskStatus(task, Task.Status.CREATED);
        task.setTaskStatus(Task.Status.IN_PROGRESS);
    }

    public void changeTaskStatusToInReview(Task task) {
        validateTaskStatus(task, Task.Status.IN_PROGRESS);
        int totalTime = task.getTotalTime();
        task.setTaskStatus(Task.Status.IN_REVIEW);
        task.setTotalTime(totalTime);
    }

    public void changeTaskStatusToCompleted(Task task) {
        validateTaskStatus(task, Task.Status.IN_REVIEW);
        task.setTaskStatus(Task.Status.COMPLETED);
    }

    private void validateTaskStatus(Task task, Task.Status expectedStatus) {
        if (task.getTaskStatus() != expectedStatus) {
            throw new IllegalArgumentException("Invalid task status for this operation");
        }
    }

    @Override
    public List<Task> viewAllTasksCreatedByManager(Manager manager) {
        List<Task> tasksCreatedByManager = new ArrayList<>();
        for (Task task : taskRepo.findAll()) {
            if (task.getCreatedBy().equals(manager)) {
                tasksCreatedByManager.add(task);
            }
        }
        if (tasksCreatedByManager.isEmpty()) {
            logger.info("No Task created by you");
        }
        return tasksCreatedByManager;
    }
    @Override
    public void updateTask(UserDto authenticatedUser, TaskDto taskDto) {
        Task existingTask = taskRepo.getTaskByTaskTitle(taskDto.getTitle());
        validateIfUserCanArchiveTask(authenticatedUser,taskDto,existingTask);
        validateIfUserCanChangeStatus(authenticatedUser,taskDto,existingTask);
        validateIfUserCanAssignTask(authenticatedUser,taskDto,existingTask);
        Task incomingTask = new Task();
        BeanUtils.copyProperties(existingTask,incomingTask);
        Task updateTask = TaskMapper(existingTask,taskDto);
        taskRepo.save(updateTask);
    }
    private Task TaskMapper(Task existingTask,TaskDto incomingTask) {
        existingTask.setTaskStatus(incomingTask.getStatus());
        existingTask.setDescription(incomingTask.getDescription());
        existingTask.setTotalTime(incomingTask.getTotalTime());
        if(incomingTask.isAssigned()) {
            existingTask.setAssigned(false);
            existingTask.setAssignee(null);
        } else{
            existingTask.setAssignee(userRepo.getUserByUserName(incomingTask.getAssignee()));
            existingTask.setAssigned(incomingTask.isAssigned());
        }
        return existingTask;
    }
    private void validateIfUserCanArchiveTask(UserDto authenticatedUser, TaskDto taskDto, Task existingTask) {
        boolean isTaskNeedToArchive = !Objects.equals(taskDto.isAssigned(), existingTask.isAssigned());
        boolean isSupervisor = Objects.equals(authenticatedUser.getUserType(), User.UserType.SUPERVISOR);
        if (isTaskNeedToArchive && !isSupervisor){
            logger.error("Only supervisor can archive a task. User {} is not a supervisor", authenticatedUser);
            throw new BadRequestException("Only supervisor can archive task");
        }
    }
    public void validateIfUserCanChangeStatus(UserDto authenticatedUser, TaskDto taskDto, Task existingTask) {
        Task.Status currentStatus = existingTask.getTaskStatus();
        Task.Status inComingTaskStatus = taskDto.getStatus();

        if (isTaskStatusNeedToChange(currentStatus, inComingTaskStatus)) {
            if (isTaskCompleted(currentStatus)) {
                throw new BadRequestException("Task is completed");
            } else if (!isTaskOwner(authenticatedUser, existingTask) && (!isManager(authenticatedUser)) || (!isEmployee(authenticatedUser))) {
                throw new ForbiddenAccessException("User is not able to change status");
            } else if (isTaskInProgress(currentStatus) && isTaskInReview(inComingTaskStatus)) {
                throw new ForbiddenAccessException("The time to change status from IN_PROGRESS to IN_REVIEW is not yet reached");
            } else if (isManager(authenticatedUser) && isTaskInReview(inComingTaskStatus)) {
                throw new ForbiddenAccessException("Manager cannot change task to IN_REVIEW");
            } else if (isManager(authenticatedUser) && isTaskCompleted(currentStatus) && isTaskCompleted(inComingTaskStatus)) {
                throw new ForbiddenAccessException("Manager cannot change task to COMPLETE");
            } else if (isEmployee(authenticatedUser) && isTaskCompleted(inComingTaskStatus)) {
                throw new ForbiddenAccessException("Employee cannot change task to COMPLETE");
            }
        }
    }
    private boolean isTaskStatusNeedToChange(Task.Status currentStatus, Task.Status inComingTaskStatus) {
        return !Objects.equals(currentStatus, inComingTaskStatus);
    }
    private boolean isTaskCompleted(Task.Status status) {
        return status == Task.Status.COMPLETED;
    }
    private boolean isTaskOwner(UserDto authenticatedUser, Task existingTask) {
        return existingTask.getAssignee() != null && existingTask.getAssignee().getUserName().equals(authenticatedUser.getUserName());
    }
    private boolean isTaskInProgress(Task.Status status) {
        return status == Task.Status.IN_PROGRESS;
    }
    private boolean isTaskInReview(Task.Status status) {
        return status == Task.Status.IN_REVIEW;
    }
//    public void validateIfUserCanChangeStatus(UserDto authenticatedUser, TaskDto taskDto, Task existingTask) {
//        Task.Status currentStatus = existingTask.getTaskStatus();
//        Task.Status inComingTaskStatus = taskDto.getStatus();
//        boolean isTaskStatusNeedToChange = !Objects.equals(currentStatus, inComingTaskStatus);
//        boolean isSupervisor = Objects.equals(authenticatedUser.getUserType(), User.UserType.SUPERVISOR);
//        boolean isManager = Objects.equals(authenticatedUser.getUserType(), User.UserType.MANAGER);
//        boolean isEmployee = Objects.equals(authenticatedUser.getUserType(), User.UserType.EMPLOYEE);
//        boolean changeTaskAccess = existingTask.getAssignee() != null && existingTask.getAssignee().getUserName().equals(authenticatedUser.getUserName());
//        if (isTaskStatusNeedToChange && currentStatus == Task.Status.CREATED && (taskDto.getStatus() == Task.Status.IN_REVIEW || taskDto.getStatus() == Task.Status.COMPLETED)) {
//            logger.error(" Task is in undesirable State");
//        } else if (isTaskStatusNeedToChange && (currentStatus == Task.Status.COMPLETED )) {
//            logger.error("Task is completed ");
//        } else if (isTaskStatusNeedToChange && !changeTaskAccess && !isManager) {
//            logger.error("You do not have access to change Status");
//        } else if (isTaskStatusNeedToChange && currentStatus.equals(Task.Status.IN_PROGRESS) && inComingTaskStatus.equals(Task.Status.IN_REVIEW)) {
//            Instant endTime = Instant.now();
//            Instant startInstant = existingTask.getStartTime().atZone(ZoneId.systemDefault()).toInstant();
//            Instant endInstant = endTime.atZone(ZoneId.systemDefault()).toInstant();
//            Duration duration = Duration.between(startInstant, endInstant);
//            long minutes = duration.toMinutes();
//            if (!(minutes >= existingTask.getTotalTime())) {
//                throw new BadRequestException("User is not able to validate the task");
//            }
//        } else if (isTaskStatusNeedToChange && isSupervisor) {
//            throw new ForbiddenAccessException("User is not able to validate the task");
//        } else if (isTaskStatusNeedToChange && isManager && (inComingTaskStatus.equals(Task.Status.IN_PROGRESS) || inComingTaskStatus.equals(Task.Status.IN_REVIEW))) {
//            throw new ForbiddenAccessException("User is not able to validate the task");
//        }
//        else if (isTaskStatusNeedToChange && isManager && currentStatus.equals(Task.Status.IN_PROGRESS) && inComingTaskStatus.equals(Task.Status.COMPLETED)) {
//            throw new ForbiddenAccessException("");
//        } else if (isTaskStatusNeedToChange && isEmployee && inComingTaskStatus.equals(Task.Status.COMPLETED)) {
//            throw new ForbiddenAccessException("User is not able to validate the task");
//        }
//  }

    public void validateIfUserCanAssignTask(UserDto authenticatedUser, TaskDto taskDto,Task existingTask) {
        if(taskDto.getAssignee()!=null) {
            boolean isTaskNeedToAssign = !Objects.equals(taskDto.getAssignee(), existingTask.getAssignee().getUserName());
            boolean isUserManager = Objects.equals(authenticatedUser.getUserType(), User.UserType.MANAGER);
            String assignerUserName = authenticatedUser.getUserName();
            String createdByUserName = existingTask.getCreatedBy().getUserName();
            //boolean assigneeExist = userDao.existsByUsername(taskDto.getAssignee());
            boolean accessToAssign = assignerUserName.equals(createdByUserName);

            if (isTaskNeedToAssign && taskDto.isAssigned()) {
                throw new BadRequestException("User is not able to validate the task");
            }
            else if (isTaskNeedToAssign && !isUserManager) {
                throw new ForbiddenAccessException("User is not able to validate the task");
            }
            else if (isTaskNeedToAssign && !accessToAssign) {
                throw new ForbiddenAccessException("User is not able to validate the task");
            }
//            } else if (isTaskNeedToAssign && !assigneeExist) {
//                throw new BadRequestException("User is not able to validate the task");
//
//            }
            else if (isTaskNeedToAssign && taskDto.getAssignee().equals(createdByUserName)) {
                throw new ForbiddenAccessException("User is not able to validate the task");
            }
        }
    }
    @Override
    public void createTask(TaskDto taskDto, UserDto authenticatedUser){
        validateManager(authenticatedUser);
        Task task = new Task(taskDto.getTitle(),taskDto.getDescription(),taskDto.getTotalTime());
        task.setCreatedBy(convertUserNameToUser(authenticatedUser.getUserName()));
        task.setCreatedAt(Instant.now());
        taskRepo.save(task);
    }

    private void validateManager(UserDto authenticatedUser) {
        if (!isManager(authenticatedUser)) {
            throw new ForbiddenAccessException("Unauthorized");
        }
    }
    private boolean isManager(UserDto userDto) {
        return userDto.getUserType() == User.UserType.MANAGER;
    }
    private void validateEmployee(UserDto authenticatedUser) {
        if (!isEmployee(authenticatedUser)) {
            throw new ForbiddenAccessException("Unauthorized");
        }
    }
    private boolean isEmployee(UserDto userDto) {
        return userDto.getUserType() == User.UserType.EMPLOYEE;
    }
    private User convertUserNameToUser(String userName) {
        User user = userRepo.getUserByUserName(userName);
        if (Objects.isNull(user)) {
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public Manager convertUserNameToManager(String username) {
        Manager manager = managerRepo.getManagersByUserName(username);
        if (Objects.isNull(manager)) {
            throw new UserNotFoundException("Manager not found");
        }
        return manager;
    }
    @Override
    public Task getTaskByTitle(String title) {
        for (Task task : taskRepo.findAll()) {
            if (title.equalsIgnoreCase(task.getTaskTitle())) {
                return task;
            }
        }
        throw new BadRequestException("task doesn't exist");
    }
    @Override
    public List<Task> viewAssignedTasks(Employee employee) {
        List<Task> assignedTasks = new ArrayList<>();
            for (Task task : taskRepo.findAll()) {
                if (task.isAssigned() && task.getAssignee().equals(employee)) {
                    assignedTasks.add(task);
                }
            }
        return assignedTasks;
    }

@Override
public void assignTask(Manager manager, Task task, Employee employee) {
    if (task.isAssigned()) {
        logger.info("Task Already Assigned");
    }
    if (!manager.equals(task.getCreatedBy())) {
        throw new ForbiddenAccessException("You can only assign tasks created by you.");
    }
    task.setAssignee(employee);
    task.setAssigned(true);
    logger.info("Task '{}' assigned to employee '{}'", task.getTaskTitle(), employee.getUserName());
}

    @Override
    public void archiveTask(TaskDto taskDto, UserDto authenticatedUser) {
        Task task = getTaskByTitle(taskDto.getTitle());
        validateSupervisor(authenticatedUser);
        if (!Objects.isNull(task)) {
            task.setAssigned(false);
            logger.info("Task Archived");
        }
    }
    private boolean isSupervisor(UserDto userDto) {
        return userDto.getUserType() == User.UserType.SUPERVISOR;
    }
    private void validateSupervisor(UserDto authenticatedUser) {
        if (!isSupervisor(authenticatedUser)) {
            throw new ForbiddenAccessException("Unauthorized");
        }
    }
    public void printAllTasks(List<Task> tasks) {
        System.out.println("All Tasks:");
        for (Task task : tasks) {
            System.out.println("Title: " + task.getTaskTitle());
            System.out.println("Description: " + task.getDescription());
            System.out.println("Total Time: " + task.getTotalTime());
            System.out.println("Created By: " + task.getCreatedBy());
            System.out.println("Created At: " + task.getCreatedAt());
            System.out.println("Status: " + task.getTaskStatus());
            System.out.println();
        }
    }

}
//    public List<TaskDto> getTasksByQueryParameters(UserDto authenticatedUser, QueryParameterDto queryParameters) {
//        validateIfSupervisorCanViewAllTasks(authenticatedUser, queryParameters);
//        validateIfManagerCanViewAllTasks(authenticatedUser, queryParameters);
//        validateIfEmployeeCanViewAllTasks(authenticatedUser, queryParameters);
//        List<Task> tasks= taskRepo.filterTasksByParameters(authenticatedUser,queryParameters);
//
//    }
//    private void validateIfSupervisorCanViewAllTasks(UserDto authenticatedUser, QueryParameterDto queryParameters) {
//        boolean isSupervisor = Objects.equals(authenticatedUser.getUserType(), User.UserType.SUPERVISOR);
//        String queryUserName =queryParameters.getUsername();
//        User queryUser = userRepo.findUserByUsername(queryUserName);
//        boolean canseeOtherSupervisorTasks=queryUser!=null && queryUser.getUserType().equals(User.UserType.SUPERVISOR)
//                && !authenticatedUser.getUserName().equals(queryUser.getUserName());
//        if (isSupervisor && canseeOtherSupervisorTasks) {
//            throw new ForbiddenAccessException("User Not Validated");
//        }
//    }
//    private void validateIfManagerCanViewAllTasks(UserDto authenticatedUser, QueryParameterDto queryParameters) {
//        User.UserType userType = authService.getUserType(authenticatedUser);
//        boolean isManager = Objects.equals(authenticatedUser.getUserType(), User.UserType.MANAGER);
//        String queryUserName = queryParameters.getUsername();
//        User queryUser = userRepo.findUserByUsername(queryUserName);
//        boolean others = queryUser!=null &&(queryUser.getUserType().equals(User.UserType.SUPERVISOR));
//        boolean sameManager = queryUser!=null && !Objects.equals(authenticatedUser.getUserName(), queryUser.getUserName())
//                && queryUser.getUserType().equals(userType);
//        if(isManager && sameManager){
//            //log
//            throw new ForbiddenAccessException("User Not Validated");
//        }
//        if(isManager && others){
//            throw new ForbiddenAccessException("User Not Validated");
//        }
//    }
//    private void validateIfEmployeeCanViewAllTasks(UserDto authenticatedUser, QueryParameterDto queryParameters) {
//        boolean isEmployee = Objects.equals(authenticatedUser.getUserType(), User.UserType.EMPLOYEE);
//        String queryUserName = queryParameters.getUsername();
//        User queryUser = userRepo.findUserByUsername(queryUserName);
//        boolean canseeOtherEmployeeTasks = queryUser!=null && !queryUser.getUserName().equals(authenticatedUser.getUserName());
//        if (isEmployee && canseeOtherEmployeeTasks){
//            throw new ForbiddenAccessException("User Not Validated");
//        }
//    }