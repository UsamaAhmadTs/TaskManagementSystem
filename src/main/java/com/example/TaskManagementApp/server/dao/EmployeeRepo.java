
package com.example.TaskManagementApp.server.dao;

import com.example.TaskManagementApp.server.entities.Task;
import com.example.TaskManagementApp.server.entities.User;
import com.example.TaskManagementApp.server.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, String> {
    Employee getEmployeesByUserName(String UserName);

}
