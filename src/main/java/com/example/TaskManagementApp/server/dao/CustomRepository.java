package com.example.TaskManagementApp.server.dao;

import com.example.TaskManagementApp.server.dto.QueryParameterDto;
import com.example.TaskManagementApp.server.dto.UserDto;
import com.example.TaskManagementApp.server.entities.Task;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomRepository {
    List<Task> getTasks(UserDto authenticatedUser, QueryParameterDto queryParameters);
}
