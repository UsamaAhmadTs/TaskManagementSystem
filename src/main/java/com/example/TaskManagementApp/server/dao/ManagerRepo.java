
package com.example.TaskManagementApp.server.dao;

import com.example.TaskManagementApp.server.entities.Manager;
import com.example.TaskManagementApp.server.entities.Task;
import com.example.TaskManagementApp.server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerRepo extends JpaRepository<Manager, String> {
    Manager getManagersByUserName(String UserName);
}
