
package com.example.TaskManagementApp.server.dao.implementation;
import com.example.TaskManagementApp.server.dao.EmployeeRepo;
import com.example.TaskManagementApp.server.entities.Employee;
import com.example.TaskManagementApp.server.entities.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepoImplementation implements EmployeeRepo {
    private List<Employee> employees = new ArrayList<>(List.of(new Employee("usama", "1")));

    @Override
    public List<Employee> getEmployees() {
        return employees;
    }

    @Override
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    @Override
    public Employee createEmployee(String username, String password) {
        Employee newEmployee = new Employee(username, password);
        addEmployee(newEmployee);
        return newEmployee;
    }

    @Override
    public List<User> getEmployeesByName(String name) {
        List<User> employeesByName = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getUserName().equals(name)) {
                employeesByName.add(employee);
            }
        }
        return employeesByName;
    }

    @Override
    public Employee findEmployee(String username) {
        for (Employee employee : employees) {
            if (employee.getUserName().equals(username)) {
                return employee;
            }
        }
        return null;
    }

}
