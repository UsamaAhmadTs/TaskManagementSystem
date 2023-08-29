// EmployeeService.java
package com.example.TaskManagementApp.server.services.implementation;

import com.example.TaskManagementApp.server.dao.EmployeeRepo;
import com.example.TaskManagementApp.server.entities.Employee;
import com.example.TaskManagementApp.server.entities.Manager;
import com.example.TaskManagementApp.server.entities.Task;
import com.example.TaskManagementApp.server.exception.UserNotFoundException;
import com.example.TaskManagementApp.server.services.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepo employeeRepo;

    public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

//    public void addTotalTime(Employee employee, Task task, int totalTime) {
//        if (employee != null && task != null && employee.getUserType().equals("Employee")) {
//            if (task.getTaskStatus().equals("IN_PROGRESS") || task.getTaskStatus().equals("IN_REVIEW")) {
//                task.setTotalTime(totalTime);
//            }
//        }
//    }
    @Override
    public Employee convertUserNameToEmployee(String username) {
        Employee employee = employeeRepo.getEmployeesByUserName(username);
        if (Objects.isNull(employee)) {
            throw new UserNotFoundException("Employee not found");
        }
        return employee;
    }
//    public List<Employee> getEmployees() {
//        return employeeRepo.findAll();
//    }
//
//    public Employee findEmployee(String username) {
//        return employeeRepo.getEmployeesByUserName(username);
//    }

}
