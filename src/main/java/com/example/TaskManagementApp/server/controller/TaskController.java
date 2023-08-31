package com.example.TaskManagementApp.server.controller;
import com.example.TaskManagementApp.server.dto.QueryParameterDto;
import com.example.TaskManagementApp.server.dto.TaskDto;
import com.example.TaskManagementApp.server.dto.UserDto;
import com.example.TaskManagementApp.server.dto.UsernamePasswordDto;
import com.example.TaskManagementApp.server.entities.Task;
import com.example.TaskManagementApp.server.services.AuthService;
import com.example.TaskManagementApp.server.services.TaskService;
import com.example.TaskManagementApp.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final UserService userService;
    private final AuthService authService;
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService,UserService userService, AuthService authService) {
        this.taskService = taskService;
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/")
    public ResponseEntity<String> createTask(@RequestBody TaskDto taskDto, @RequestHeader("Authorization") String authorizationHeader) {
        UsernamePasswordDto usernamePassword = authService.extractUsernamePassword(authorizationHeader);
        UserDto authenticatedUser = userService.verifyUserCredentials(usernamePassword);
        taskService.createTask(taskDto, authenticatedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("Task created successfully");
    }

    @GetMapping("/")
    public ResponseEntity<List<TaskDto>> getTasks(@RequestParam("status") Task.Status status,
                                                           @RequestParam String userName,
                                                           @RequestHeader("Authorization") String authorizationHeader) {
        UsernamePasswordDto usernamePassword = authService.extractUsernamePassword(authorizationHeader);
        UserDto authenticatedUser = userService.verifyUserCredentials(usernamePassword);
        QueryParameterDto queryParameterDto = QueryParameterDto.create(userName,status);
        List<TaskDto> tasks = taskService.getTasksByQueryParameters(authenticatedUser, queryParameterDto);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/")
    public ResponseEntity<String> update(
            @RequestHeader("Authorization") String authorizationHeader, @RequestBody TaskDto taskDto) {
        UsernamePasswordDto usernamePassword = authService.extractUsernamePassword(authorizationHeader);
        UserDto authenticatedUser = userService.verifyUserCredentials(usernamePassword);
        taskService.updateTask(authenticatedUser, taskDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}