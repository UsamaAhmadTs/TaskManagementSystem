package com.example.TaskManagementApp.server.services;

import com.example.TaskManagementApp.server.dto.TaskDto;
import com.example.TaskManagementApp.server.dto.UserDto;
import com.example.TaskManagementApp.server.entities.Employee;
import com.example.TaskManagementApp.server.entities.Manager;
import com.example.TaskManagementApp.server.entities.Task;

import java.util.List;

public interface TaskService {

    void assignTask(Manager manager, Task task, Employee employee);
    void UpdateStatus(Task task, Task.Status status,UserDto authenticatedUser);
    List<Task> getAllTasks();
    void printAllTasks(List<Task> tasks);
    List<Task> viewAllTasksCreatedByManager(Manager manager);

    List<Task> viewAllTasksByStatus(Task.Status status);

    Task getTaskByTitle(String title);

    void archiveTask(TaskDto taskDto,UserDto authenticatedUser);

    List<Task> viewAssignedTasks(Employee employee);

    void createTask(TaskDto taskDto, UserDto authenticatedUser);

    Manager convertUserNameToManager(String userName);
}
