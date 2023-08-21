// UserRepository.java
package com.example.TaskManagementApp.server.dao;

import com.example.TaskManagementApp.server.entities.User;

import java.util.List;

public interface UserRepo {
    List<User> getUsers();

    void addUser(User user);

    User findUserByUsername(String username);

    void initializeUsers();
}
