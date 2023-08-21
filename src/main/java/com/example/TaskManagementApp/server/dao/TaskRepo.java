package com.example.TaskManagementApp.server.dao;

import com.example.TaskManagementApp.server.dto.TaskDto;
import com.example.TaskManagementApp.server.entities.Task;

import java.util.List;

public interface TaskRepo {

    List<Task> getAllTask();
    List<Task> task();

    void addTask(TaskDto taskDto);


}