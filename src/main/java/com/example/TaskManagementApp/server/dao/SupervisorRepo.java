package com.example.TaskManagementApp.server.dao;

import com.example.TaskManagementApp.server.entities.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupervisorRepo extends JpaRepository<Supervisor, String> {
    Supervisor getSupervisorByUserNameAndPassword(String UserName, String Password);
}
