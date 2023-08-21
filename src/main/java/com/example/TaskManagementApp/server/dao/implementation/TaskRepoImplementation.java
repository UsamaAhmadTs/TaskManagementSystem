package com.example.TaskManagementApp.server.dao.implementation;

import com.example.TaskManagementApp.server.dao.TaskRepo;
import com.example.TaskManagementApp.server.dto.TaskDto;
import com.example.TaskManagementApp.server.entities.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class TaskRepoImplementation implements TaskRepo {
    private List<Task> tasks = new ArrayList<>();
    @Override
    public List<Task> getAllTask() {
        return tasks;
    }
    public List<Task> task(){
        return  tasks;
    }
    @Override
    public void addTask(TaskDto taskDto) {
       this.tasks.add(Task.fromDto(taskDto));
    }
}
