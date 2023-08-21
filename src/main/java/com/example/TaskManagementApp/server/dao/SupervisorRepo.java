package com.example.TaskManagementApp.server.dao;

import com.example.TaskManagementApp.server.entities.Supervisor;

import java.util.List;

public interface SupervisorRepo {
    List<Supervisor> getSupervisors();
    Supervisor findSupervisorByUsernameAndPassword(String username, String password);

    void addSupervisor(Supervisor supervisor);
}
