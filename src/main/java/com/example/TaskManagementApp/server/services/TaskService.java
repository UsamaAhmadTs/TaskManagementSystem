package com.example.TaskManagementApp.server.services;

import com.example.TaskManagementApp.server.dto.TaskDto;
import com.example.TaskManagementApp.server.dto.UserDto;
import com.example.TaskManagementApp.server.entities.Employee;
import com.example.TaskManagementApp.server.entities.Manager;
import com.example.TaskManagementApp.server.entities.Task;

import java.util.List;

public interface TaskService {
    void updateTask(UserDto authenticatedUser, TaskDto taskDTO);

    List<Task> getAllTasks();
    List<Task> viewAllTasksCreatedByManager(Manager manager);

    List<Task> viewAllTasksByStatus(Task.Status status);

    Task getTaskByTitle(String title);

    List<Task> viewAssignedTasks(Employee employee);

    void createTask(TaskDto taskDto, UserDto authenticatedUser);

    Manager convertUserNameToManager(String userName);
}
