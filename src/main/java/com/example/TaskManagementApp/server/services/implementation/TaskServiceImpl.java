package com.example.TaskManagementApp.server.services.implementation;
import com.example.TaskManagementApp.server.dao.*;
import com.example.TaskManagementApp.server.dto.QueryParameterDto;
import com.example.TaskManagementApp.server.dto.TaskDto;
import com.example.TaskManagementApp.server.dto.UserDto;
import com.example.TaskManagementApp.server.entities.Task;
import com.example.TaskManagementApp.server.entities.User;
import com.example.TaskManagementApp.server.exception.BadRequestException;
import com.example.TaskManagementApp.server.exception.ForbiddenAccessException;
import com.example.TaskManagementApp.server.exception.UserNotFoundException;
import com.example.TaskManagementApp.server.services.AuthService;
import com.example.TaskManagementApp.server.services.TaskService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepo taskRepo;
    private final UserRepo userRepo;
    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
@Autowired
    public TaskServiceImpl(AuthService authService,UserRepo userRepo, TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
        this.authService= authService;
        this.userRepo= userRepo;
    }
    public List<TaskDto> getTasksByQueryParameters(UserDto authenticatedUser, QueryParameterDto queryParameters) {
        validateIfSupervisorCanViewTasks(authenticatedUser, queryParameters);
        validateIfManagerCanViewTasks(authenticatedUser, queryParameters);
        validateIfEmployeeCanViewTasks(authenticatedUser, queryParameters);
        List<Task> tasks = taskRepo.getTasks(authenticatedUser, queryParameters);
        return mapTasksToTaskDtos(tasks);
    }
    private List<TaskDto> mapTasksToTaskDtos(List<Task> tasks) {
        List<TaskDto> taskDtos=new ArrayList<>();
        for(Task task:tasks)
        {
            TaskDto taskDto=new TaskDto();
            taskDto.setTitle(task.getTaskTitle());
            taskDto.setDescription(task.getDescription());
            taskDto.setStatus(task.getTaskStatus());
            taskDto.setAssignee(Objects.isNull(task.getAssignee()) ? null : task.getAssignee().getUserName());
            taskDto.setAssigned(task.isAssigned());
            taskDto.setCreatedAt(task.getCreatedAt());
            taskDto.setCreatedBy(task.getCreatedBy().getUserName());
            taskDto.setTotalTime(task.getTotalTime());
            taskDtos.add(taskDto);
        }
        return taskDtos;
    }
    private void validateIfSupervisorCanViewTasks(UserDto authenticatedUser, QueryParameterDto queryParameters) {
        boolean isSupervisor = Objects.equals(authenticatedUser.getUserType(), User.UserType.SUPERVISOR);
        String queryUserName =queryParameters.getUsername();
        User queryUser = userRepo.getUserByUserName(queryUserName);
        boolean otherSupervisorTasks=queryUser!=null && queryUser.getUserType().equals(User.UserType.SUPERVISOR)
                && !authenticatedUser.getUserName().equals(queryUser.getUserName());
        if (isSupervisor && otherSupervisorTasks) {
            throw new ForbiddenAccessException("User Not Validated");
        }
    }
    private void validateIfManagerCanViewTasks(UserDto authenticatedUser, QueryParameterDto queryParameters) {
        User.UserType userType = authService.getUserType(authenticatedUser);
        boolean isManager = Objects.equals(authenticatedUser.getUserType(), User.UserType.MANAGER);
        String queryUserName = queryParameters.getUsername();
        User queryUser = userRepo.getUserByUserName(queryUserName);
        boolean others = queryUser!=null &&(queryUser.getUserType().equals(User.UserType.SUPERVISOR));
        boolean otherManagerTasks = queryUser!=null && !Objects.equals(authenticatedUser.getUserName(), queryUser.getUserName())
                && queryUser.getUserType().equals(userType);
        if(isManager && otherManagerTasks){
            throw new ForbiddenAccessException("User Not Validated");
        }
        if(isManager && others){
            throw new ForbiddenAccessException("User Not Validated");
        }
    }
    private void validateIfEmployeeCanViewTasks(UserDto authenticatedUser, QueryParameterDto queryParameters) {
        boolean isEmployee = Objects.equals(authenticatedUser.getUserType(), User.UserType.EMPLOYEE);
        String queryUserName = queryParameters.getUsername();
        User queryUser = userRepo.getUserByUserName(queryUserName);
        boolean otherEmployeeTasks = queryUser!=null && !queryUser.getUserName().equals(authenticatedUser.getUserName());
        if (isEmployee && otherEmployeeTasks){
            throw new ForbiddenAccessException("User Not Validated");
        }
    }

    @Override
    public void updateTask(UserDto authenticatedUser, TaskDto taskDto) {
        Task existingTask = taskRepo.getTaskByTaskTitle(taskDto.getTitle());
        validateIfUserCanArchiveTask(authenticatedUser,taskDto,existingTask);
        validateIfUserCanAssignTask(authenticatedUser,taskDto,existingTask);
        validateIfUserCanChangeStatus(authenticatedUser,taskDto,existingTask);
        Task incomingTask = new Task();
        BeanUtils.copyProperties(existingTask,incomingTask);
        Task updateTask = TaskMapper(existingTask,taskDto);

        taskRepo.save(updateTask);
    }
    private Task TaskMapper(Task existingTask,TaskDto incomingTask) {
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

}
