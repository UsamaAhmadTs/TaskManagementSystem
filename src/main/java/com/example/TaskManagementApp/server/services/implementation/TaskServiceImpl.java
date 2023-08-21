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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
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
    private final TaskHistoryRepo taskHistoryRepo;
    private static TaskServiceImpl instance;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public TaskServiceImpl(EmployeeService employeeService,UserRepo userRepo, ManagerService managerService, TaskRepo taskRepo, EmployeeRepo employeeRepo, ManagerRepo managerRepo, TaskHistoryRepo taskHistoryRepo) {
        this.employeeService = employeeService;
        this.managerServiceImpl = managerService;
        this.taskRepo = taskRepo;
        this.employeeRepo = employeeRepo;
        this.managerRepo = managerRepo;
        this.taskHistoryRepo = taskHistoryRepo;

        this.userRepo= userRepo;

    }
    @Override
    public List<Task> viewAllTasksByStatus(Task.Status status){
        List<Task> tasksByStatus = new ArrayList<>();
        for (Task task : taskRepo.getAllTask()) {
            if (task.getTaskStatus() == status) {
                tasksByStatus.add(task);
            }
        }
        return tasksByStatus;
    }
    @Override
    public List<Task> getAllTasks() {
        return taskRepo.getAllTask();
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
        for (Task task : taskRepo.getAllTask()) {
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
    public void createTask(TaskDto taskDto, UserDto authenticatedUser){
        validateManager(authenticatedUser);
        taskDto.setCreatedBy(convertUserNameToUser(authenticatedUser.getUserName()));
        taskDto.setCreatedAt(Instant.now());
        taskRepo.addTask(taskDto);
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
        User user = userRepo.findUserByUsername(userName);
        if (Objects.isNull(user)) {
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public Manager convertUserNameToManager(String username) {
        Manager manager = managerRepo.findManager(username);
        if (Objects.isNull(manager)) {
            throw new UserNotFoundException("Manager not found");
        }
        return manager;
    }
    @Override
    public Task getTaskByTitle(String title) {
        for (Task task : taskRepo.getAllTask()) {
            if (title.equalsIgnoreCase(task.getTitle())) {
                return task;
            }
        }
        throw new BadRequestException("task doesn't exist");
    }
    @Override
    public List<Task> viewAssignedTasks(Employee employee) {
        List<Task> assignedTasks = new ArrayList<>();
            for (Task task : taskRepo.getAllTask()) {
                if (task.isAssigned() && task.getAssignedTo().equals(employee)) {
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
    task.setAssignedTo(employee);
    task.setAssignee(manager);
    task.setAssigned(true);
    logger.info("Task '{}' assigned to employee '{}'", task.getTitle(), employee.getUserName());
}

    @Override
    public void archiveTask(TaskDto taskDto, UserDto authenticatedUser) {
        Task task = getTaskByTitle(taskDto.getTitle());
        validateSupervisor(authenticatedUser);
        if (!Objects.isNull(task)) {
            task.setAssigned(false);
            task.setAssignee(null);
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
            System.out.println("Title: " + task.getTitle());
            System.out.println("Description: " + task.getDescription());
            System.out.println("Total Time: " + task.getTotalTime());
            System.out.println("Created By: " + task.getCreatedBy());
            System.out.println("Created At: " + task.getCreatedAt());
            System.out.println("Status: " + task.getTaskStatus());
            System.out.println("Assignee: " + task.getAssignee());
            System.out.println();
        }
    }

}
