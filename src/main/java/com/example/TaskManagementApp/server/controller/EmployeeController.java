//package com.example.TaskManagementApp.server.controller;
//
//import com.example.TaskManagementApp.server.entities.Employee;
//import com.example.TaskManagementApp.server.services.EmployeeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/employees")
//public class EmployeeController {
//
//    private final EmployeeService employeeService;
//
//    @Autowired
//    public EmployeeController(EmployeeService employeeService) {
//        this.employeeService = employeeService;
//    }
//
//    @GetMapping("/")
//    public List<Employee> getAllEmployees() {
//        return employeeService.getAllEmployees();
//    }
//    @GetMapping("/{username}")
//    public Employee findEmployee(@PathVariable String username) {
//        return employeeService.findEmployee(username);
//    }
//}
