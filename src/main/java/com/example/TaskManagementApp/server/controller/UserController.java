package com.example.TaskManagementApp.server.controller;

import com.example.TaskManagementApp.server.dto.UserDto;
import com.example.TaskManagementApp.server.dto.UsernamePasswordDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.TaskManagementApp.server.services.UserService;
import com.example.TaskManagementApp.server.services.AuthService;
import com.example.TaskManagementApp.server.entities.User;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    // Baisc Auth
    // Restfull convention only on url
    // Dto pattern
    //controller advice exception catch
    //exception handle
    //Loging slf4j
    //response entity
    private final UserService userService;
    private final AuthService authService;


    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

@PostMapping("/")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto, @RequestHeader("Authorization") String authorizationHeader) {
        UsernamePasswordDto usernamePassword = authService.extractUsernamePassword(authorizationHeader);
        UserDto user = userService.verifyUserCredentials(usernamePassword);
        userService.createUser(userDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/")
    public List<User> getAllUsers(@RequestHeader("Authorization") String authorizationHeader) {
        UsernamePasswordDto usernamePassword = authService.extractUsernamePassword(authorizationHeader);
        UserDto user = userService.verifyUserCredentials(usernamePassword);
        return userService.getAllUsers(user);
    }
//    @GetMapping("/verify")
//    public User verifyUserCredentials(String username, String password) {
//        return userService.verifyUserCredentials(username, password);
//    }
}
