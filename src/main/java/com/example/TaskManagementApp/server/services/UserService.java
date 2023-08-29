
package com.example.TaskManagementApp.server.services;

import com.example.TaskManagementApp.server.dto.UserDto;
import com.example.TaskManagementApp.server.dto.UsernamePasswordDto;
import com.example.TaskManagementApp.server.entities.User;

import java.util.List;

public interface UserService {
    void createUser(UserDto userDto, UserDto authenticatedUser);

    List<User> getAllUsers(UserDto authenticatedUser, User.UserType userType);

    User verifyUserCredentials(String username, String password);

    UserDto verifyUserCredentials(UsernamePasswordDto usernamePassword);

    void initializeUsers();
}
