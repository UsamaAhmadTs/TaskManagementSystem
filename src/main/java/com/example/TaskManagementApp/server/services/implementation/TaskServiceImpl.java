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
        validateIfUserCanAssignTask(authenticatedUser,taskDto,existingTask);
        validateIfUserCanChangeStatus(authenticatedUser,taskDto,existingTask);
        Task incomingTask = new Task();
        BeanUtils.copyProperties(existingTask,incomingTask);
        Task updateTask = TaskMapper(existingTask,taskDto,authenticatedUser);

        taskRepo.save(updateTask);
    }
    private Task TaskMapper(Task existingTask,TaskDto incomingTask,UserDto authenticatedUser) {
        existingTask.setTaskStatus(incomingTask.getStatus());
        existingTask.setDescription(incomingTask.getDescription());
        existingTask.setTotalTime(incomingTask.getTotalTime());
        existingTask.setAssignee(userRepo.getUserByUserName(incomingTask.getAssignee()));
        existingTask.setAssigned(incomingTask.isAssigned());
        return existingTask;
    }
    private void validateIfUserCanArchiveTask(UserDto authenticatedUser, TaskDto taskDto, Task existingTask) {
        boolean isTaskNeedToArchive = !Objects.equals(taskDto.isAssigned(), existingTask.isAssigned());
        boolean isSupervisor = Objects.equals(authenticatedUser.getUserType(), User.UserType.SUPERVISOR);
        if (isTaskNeedToArchive && !isSupervisor&& existingTask.getAssignee()!=null){
            throw new BadRequestException("Only supervisor can archive task");
        }
    }
    public void validateIfUserCanChangeStatus(UserDto authenticatedUser, TaskDto taskDto, Task existingTask) {
        Task.Status currentStatus = existingTask.getTaskStatus();
        Task.Status inComingTaskStatus = taskDto.getStatus();
        if(currentStatus.equals(Task.Status.CREATED) && inComingTaskStatus.equals(Task.Status.IN_PROGRESS)) {
            existingTask.setStartTime(Instant.now());
            taskRepo.save(existingTask);
        }
        if (isTaskStatusNeedToChange(currentStatus, inComingTaskStatus)) {
            if (isTaskCompleted(currentStatus)) {
                throw new BadRequestException("Task is completed");
            } else if (!isTaskOwner(authenticatedUser, existingTask) && !isEmployee(authenticatedUser)) {
                throw new ForbiddenAccessException("User is not able to change status");
            } else if (isManager(authenticatedUser) && (isTaskInReview(inComingTaskStatus) || isTaskInProgress(inComingTaskStatus))) {
                throw new ForbiddenAccessException("Manager cannot change task to IN_REVIEW or IN_PROGRESS");
            } else if (isEmployee(authenticatedUser) && currentStatus == Task.Status.IN_PROGRESS && inComingTaskStatus == Task.Status.IN_REVIEW) {
                Instant startTime = existingTask.getStartTime().atZone(ZoneId.systemDefault()).toInstant();
                long elapsedMinutes = Duration.between(startTime, Instant.now()).toMinutes();
                if (elapsedMinutes < existingTask.getTotalTime()) {
                    throw new BadRequestException("User is not able to validate the task");
                }
            } else if (isEmployee(authenticatedUser) && isTaskCompleted(inComingTaskStatus)) {
                throw new ForbiddenAccessException("Employee cannot change task to COMPLETE");
            }else if (isEmployee(authenticatedUser)&& currentStatus.equals(Task.Status.CREATED) && isTaskInReview(inComingTaskStatus)) {
                throw new ForbiddenAccessException("Employee cannot change task to IN_REVIEW Directly");
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
        return existingTask.getCreatedBy() != null && existingTask.getCreatedBy().getUserName().equals(authenticatedUser.getUserName());
    }
    private boolean isTaskInProgress(Task.Status status) {
        return status == Task.Status.IN_PROGRESS;
    }
    private boolean isTaskInReview(Task.Status status) {
        return status == Task.Status.IN_REVIEW;
    }
private void validateIfUserCanAssignTask(UserDto authenticatedUser, TaskDto taskDto, Task existingTask) {
    if (taskDto.getAssignee() == null) {
        return;
    }
    boolean isManager = Objects.equals(authenticatedUser.getUserType(), User.UserType.MANAGER);
    boolean isManagerSame = authenticatedUser.getUserName().equals(existingTask.getCreatedBy().getUserName());
    boolean isTaskNeedToAssign = Objects.isNull(existingTask.getAssignee());
    User assigneeExist = userRepo.getUserByUserName(taskDto.getAssignee());

    if (isTaskNeedToAssign) {
         if (!isManager) {
            throw new ForbiddenAccessException("Only Manager can assign a task.");
        } else if (!isManagerSame) {
            throw new ForbiddenAccessException("Only Manager who created this task can assign a task");
        } else if (Objects.isNull(assigneeExist) || taskDto.getAssignee().equals(existingTask.getCreatedBy().getUserName())) {
            throw new BadRequestException("User is not able to validate the task");
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