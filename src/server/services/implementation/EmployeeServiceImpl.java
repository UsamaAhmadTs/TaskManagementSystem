// EmployeeService.java
package server.services.implementation;
import server.dao.*;
import server.entities.*;
import server.services.EmployeeService;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepo employeeRepo;

    public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }
    public void printAllEmployees(List<Employee> employees) {
        System.out.println("All Employees:");
        for (Employee employee : employees) {
            System.out.println("Title: " + employee.getUsername());
            System.out.println();
        }
    }
    public void addTotalTime(Employee employee, Task task, int totalTime) {
        if (employee != null && task != null && employee.getUserType().equals("Employee")) {
            if (task.getStatus().equals("IN_PROGRESS") || task.getStatus().equals("IN_REVIEW")) {
                task.setTotalTime(totalTime);
            }
        }
    }
    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeRepo.getEmployees();
        return employees;
    }

    public Employee findEmployee(String username, String password) {
        return employeeRepo.findEmployee(username, password);
    }


}
