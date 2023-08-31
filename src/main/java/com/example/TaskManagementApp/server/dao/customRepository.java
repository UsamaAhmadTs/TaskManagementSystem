package com.example.TaskManagementApp.server.dao;

import com.example.TaskManagementApp.server.dto.QueryParameterDto;
import com.example.TaskManagementApp.server.dto.UserDto;
import com.example.TaskManagementApp.server.entities.Task;
import com.example.TaskManagementApp.server.entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface customRepository {
    List<Task> getTasks(UserDto authenticatedUser, QueryParameterDto queryParameters);
}
