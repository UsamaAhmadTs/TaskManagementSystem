
package com.example.TaskManagementApp.server.dao;

import com.example.TaskManagementApp.server.entities.Manager;
import com.example.TaskManagementApp.server.entities.Task;
import com.example.TaskManagementApp.server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ManagerRepo extends JpaRepository<Manager, String> {
    Manager getManagersByUserName(String UserName);
}
