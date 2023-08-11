package server.services;
import java.util.List;
import server.entities.Employee;
import server.entities.Task;

public interface EmployeeService {


    void addTotalTime(Employee employee, Task task, int totalTime);
    List<Employee> getAllEmployees();
    void printAllEmployees(List<Employee> employees);
    Employee findEmployee(String employee_username, String employee_password);

}
