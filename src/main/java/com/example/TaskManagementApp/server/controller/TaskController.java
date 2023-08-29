package com.example.TaskManagementApp.server.controller;
import com.example.TaskManagementApp.server.dto.TaskDto;
import com.example.TaskManagementApp.server.dto.UserDto;
import com.example.TaskManagementApp.server.dto.UsernamePasswordDto;
import com.example.TaskManagementApp.server.entities.Employee;
import com.example.TaskManagementApp.server.entities.Manager;
import com.example.TaskManagementApp.server.entities.Task;
import com.example.TaskManagementApp.server.entities.User;
import com.example.TaskManagementApp.server.exception.ForbiddenAccessException;
import com.example.TaskManagementApp.server.exception.UnauthorizedAccessException;
import com.example.TaskManagementApp.server.services.AuthService;
import com.example.TaskManagementApp.server.services.EmployeeService;
import com.example.TaskManagementApp.server.services.TaskService;
import com.example.TaskManagementApp.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final UserService userService;
    private final AuthService authService;
    private final TaskService taskService;
    private final EmployeeService employeeService;

    @Autowired
    public TaskController(TaskService taskService,UserService userService, AuthService authService,EmployeeService employeeService) {
        this.taskService = taskService;
        this.userService = userService;
        this.authService = authService;
        this.employeeService = employeeService;
    }

    @PostMapping("/")
    public ResponseEntity<String> createTask(@RequestBody TaskDto taskDto, @RequestHeader("Authorization") String authorizationHeader) {
        UsernamePasswordDto usernamePassword = authService.extractUsernamePassword(authorizationHeader);
        UserDto authenticatedUser = userService.verifyUserCredentials(usernamePassword);
        taskService.createTask(taskDto, authenticatedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("Task created successfully");
    }
//    @GetMapping("/created-by-manager")
//    public ResponseEntity<List<String>> viewAllTasksCreatedByManager(@RequestHeader("Authorization") String authorizationHeader) {
//        UsernamePasswordDto usernamePassword = authService.extractUsernamePassword(authorizationHeader);
//        UserDto authenticatedUser = userService.verifyUserCredentials(usernamePassword);
//        Manager authenticatedManager = taskService.convertUserNameToManager(authenticatedUser.getUserName());
//        List<Task> tasksCreatedByManager = taskService.viewAllTasksCreatedByManager(authenticatedManager);
//        List<String> taskTitles = tasksCreatedByManager.stream().map(Task::getTitle).collect(Collectors.toList());
//        return ResponseEntity.ok(taskTitles);
//    }

    @GetMapping("/")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/status")
    public ResponseEntity<List<Task>> viewAllTasksByStatus(@RequestParam("status") Task.Status status) {
        List<Task> tasksByStatus = taskService.viewAllTasksByStatus(status);
        return ResponseEntity.ok(tasksByStatus);
    }

    @GetMapping("/title")
    public Task getTaskByTitle(@RequestParam String title) {
        return taskService.getTaskByTitle(title);
    }
    @GetMapping("/assigned-tasks")
    public ResponseEntity<List<Task>> viewAssignedTasks(@RequestHeader("Authorization") String authorizationHeader) {
        UsernamePasswordDto usernamePassword = authService.extractUsernamePassword(authorizationHeader);
        UserDto authenticatedUser = userService.verifyUserCredentials(usernamePassword);
        Employee authenticatedEmployee = employeeService.convertUserNameToEmployee(authenticatedUser.getUserName());
        List<Task> assignedTasks = taskService.viewAssignedTasks(authenticatedEmployee);
        return ResponseEntity.ok(assignedTasks);
    }

//    @PatchMapping("/assign")
//    public ResponseEntity<String> assignTask(@RequestParam String taskTitle, @RequestParam String employeeUsername, @RequestHeader("Authorization") String authorizationHeader) {
//        UsernamePasswordDto usernamePassword = authService.extractUsernamePassword(authorizationHeader);
//        UserDto authenticatedUser = userService.verifyUserCredentials(usernamePassword);
//        Manager manager = taskService.convertUserNameToManager(authenticatedUser.getUserName());
//        Employee employee = employeeRepo.(employeeUsername);
//        Task task = taskService.getTaskByTitle(taskTitle);
//        taskService.assignTask(manager, task, employee);
//        return ResponseEntity.ok("Task assigned successfully.");
//    }

//@PatchMapping("/update")
//public ResponseEntity<?> updateTaskStatus(@RequestParam String taskTitle, @RequestParam Task.Status status,@RequestHeader("Authorization") String authorizationHeader) {
//    UsernamePasswordDto usernamePassword = authService.extractUsernamePassword(authorizationHeader);
//    UserDto authenticatedUser = userService.verifyUserCredentials(usernamePassword);
//    Task task = taskService.getTaskByTitle(taskTitle);
//    taskService.UpdateStatus(task, status, authenticatedUser);
//    return ResponseEntity.ok("Task status updated successfully");
//}
//    @PatchMapping("/archive")
//    public ResponseEntity<String> archiveTask(@RequestBody TaskDto taskDto, @RequestHeader("Authorization") String authorizationHeader) {
//        UsernamePasswordDto usernamePassword = authService.extractUsernamePassword(authorizationHeader);
//        UserDto authenticatedUser = userService.verifyUserCredentials(usernamePassword);
//        taskService.archiveTask(taskDto,authenticatedUser);
//        return ResponseEntity.ok("Task archived successfully");
//    }
    @PutMapping()
    public ResponseEntity<String> update(
            @RequestHeader("Authorization") String authorizationHeader, @RequestBody TaskDto taskDTO) {
        UsernamePasswordDto usernamePassword = authService.extractUsernamePassword(authorizationHeader);
        UserDto authenticatedUser = userService.verifyUserCredentials(usernamePassword);
        taskService.updateTask(authenticatedUser, taskDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}