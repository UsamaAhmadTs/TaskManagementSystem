package com.example.TaskManagementApp.server.services;

import com.example.TaskManagementApp.server.dto.QueryParameterDto;
import com.example.TaskManagementApp.server.dto.TaskDto;
import com.example.TaskManagementApp.server.dto.UserDto;
import com.example.TaskManagementApp.server.entities.Employee;
import com.example.TaskManagementApp.server.entities.Manager;
import com.example.TaskManagementApp.server.entities.Task;

import java.util.List;

public interface TaskService {
    void updateTask(UserDto authenticatedUser, TaskDto taskDTO);
    List<TaskDto> getTasksByQueryParameters(UserDto authenticatedUser, QueryParameterDto queryParameters);
    void createTask(TaskDto taskDto, UserDto authenticatedUser);

}
