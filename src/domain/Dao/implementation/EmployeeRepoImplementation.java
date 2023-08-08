
package domain.Dao.implementation;
import domain.Dao.EmployeeRepo;
import domain.entities.Employee;
import domain.entities.User;

import java.util.ArrayList;
import java.util.List;


public class EmployeeRepoImplementation implements EmployeeRepo {
    private List<Employee> employees = new ArrayList<>();

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
            if (employee.getUsername().equals(name)) {
                employeesByName.add(employee);
            }
        }
        return employeesByName;
    }

    @Override
    public Employee findEmployee(String username) {
        for (Employee employee : employees) {
            if (employee.getUsername().equals(username)) {
                return employee;
            }
        }
        return null;
    }

}
