package com.example.TaskManagementApp.server.dao;

import com.example.TaskManagementApp.server.dto.TaskDto;
import com.example.TaskManagementApp.server.entities.Task;
import com.example.TaskManagementApp.server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, String>, customRepository {

    Task getTaskByTaskTitle(String TaskTitle);

}