//correct formatting issue 
//use optional query parameters
package com.example.TaskManagementApp.server.controller;
import com.example.TaskManagementApp.server.dto.UserDto;
import com.example.TaskManagementApp.server.dto.UsernamePasswordDto;
import com.example.TaskManagementApp.server.entities.TaskHistory;
import com.example.TaskManagementApp.server.services.AuthService;
import com.example.TaskManagementApp.server.services.TaskHistoryService;
import com.example.TaskManagementApp.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/history")
public class TaskHistoryController {
    private final UserService userService;
    private final AuthService authService;
    private final TaskHistoryService taskHistoryService;

    @Autowired
    public TaskHistoryController(TaskHistoryService taskHistoryService,UserService userService, AuthService authService) {
        this.taskHistoryService = taskHistoryService;
        this.userService = userService;
        this.authService = authService;
    }
    @GetMapping("/")
    public ResponseEntity<List<TaskHistory>> getHistory(@RequestParam("title") String title,
                                                  @RequestHeader("Authorization") String authorizationHeader) {
        UsernamePasswordDto usernamePassword = authService.extractUsernamePassword(authorizationHeader);
        UserDto authenticatedUser = userService.verifyUserCredentials(usernamePassword);
        List<TaskHistory> tasks = taskHistoryService.getTaskHistory(authenticatedUser,title);
        return ResponseEntity.ok(tasks);
    }
}