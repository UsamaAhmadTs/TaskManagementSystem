package com.example.TaskManagementApp.server.services;
import com.example.TaskManagementApp.server.entities.Employee;
import com.example.TaskManagementApp.server.entities.Manager;
import com.example.TaskManagementApp.server.entities.Task;

import java.util.List;
public interface EmployeeService {
//    void addTotalTime(Employee employee, Task task, int totalTime);
//    List<Employee> getEmployees();
    Employee convertUserNameToEmployee(String userName);
}