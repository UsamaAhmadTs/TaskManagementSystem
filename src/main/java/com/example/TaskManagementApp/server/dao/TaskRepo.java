package com.example.TaskManagementApp.server.dao;
import com.example.TaskManagementApp.server.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends JpaRepository<Task, String>, CustomRepository {

    Task getTaskByTaskTitle(String TaskTitle);

}