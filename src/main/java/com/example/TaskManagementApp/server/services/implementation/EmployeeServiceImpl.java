//// EmployeeService.java
//package com.example.TaskManagementApp.server.services.implementation;
//
//import com.example.TaskManagementApp.server.dao.EmployeeRepo;
//import com.example.TaskManagementApp.server.entities.Employee;
//import com.example.TaskManagementApp.server.entities.Manager;
//import com.example.TaskManagementApp.server.entities.Task;
//import com.example.TaskManagementApp.server.exception.UserNotFoundException;
//import com.example.TaskManagementApp.server.services.EmployeeService;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Objects;
//
//@Service
//public class EmployeeServiceImpl implements EmployeeService {
//    private final EmployeeRepo employeeRepo;
//
//    public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
//        this.employeeRepo = employeeRepo;
//    }
//    public void printAllEmployees(List<Employee> employees) {
//        System.out.println("All Employees:");
//        for (Employee employee : employees) {
//            System.out.println("Title: " + employee.getUserName());
//            System.out.println();
//        }
//    }
//    public void addTotalTime(Employee employee, Task task, int totalTime) {
//        if (employee != null && task != null && employee.getUserType().equals("Employee")) {
//            if (task.getTaskStatus().equals("IN_PROGRESS") || task.getTaskStatus().equals("IN_REVIEW")) {
//                task.setTotalTime(totalTime);
//            }
//        }
//    }
//    @Override
//    public Employee convertUserNameToEmployee(String username) {
//        Employee employee = employeeRepo.findEmployee(username);
//        if (Objects.isNull(employee)) {
//            throw new UserNotFoundException("Employee not found");
//        }
//        return employee;
//    }
//    public List<Employee> getAllEmployees() {
//        List<Employee> employees = employeeRepo.getEmployees();
//        return employees;
//    }
//
//    public Employee findEmployee(String username) {
//        return employeeRepo.findEmployee(username);
//    }
//
//}
