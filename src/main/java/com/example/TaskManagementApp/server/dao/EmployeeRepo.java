
package com.example.TaskManagementApp.server.dao;

import com.example.TaskManagementApp.server.entities.User;
import com.example.TaskManagementApp.server.entities.Employee;

import java.util.List;

public interface EmployeeRepo {
    List<Employee> getEmployees();

    void addEmployee(Employee employee);

    Employee createEmployee(String username, String password);

    List<User> getEmployeesByName(String name);

    Employee findEmployee(String username);
}
