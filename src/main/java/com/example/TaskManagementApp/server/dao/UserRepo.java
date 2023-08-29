// UserRepository.java
package com.example.TaskManagementApp.server.dao;

import com.example.TaskManagementApp.server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, String> {
    User getUserByUserName(String UserName);
    List<User> getUsersByUserType(User.UserType UserType);
}
