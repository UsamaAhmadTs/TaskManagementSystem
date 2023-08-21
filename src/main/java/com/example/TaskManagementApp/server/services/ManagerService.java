package com.example.TaskManagementApp.server.services;

import com.example.TaskManagementApp.server.entities.Manager;
import com.example.TaskManagementApp.server.entities.Task;

import java.util.List;
    public interface ManagerService {
        List<Task> viewTasksCreatedByManager(Manager manager);
        List<Manager> getManagers();

        Manager findManager(String manager_username);
    }
