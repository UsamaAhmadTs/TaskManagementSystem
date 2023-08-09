// EmployeeService.java
package server.services.implementation;
import server.dao.*;
import server.entities.*;
import server.services.EmployeeInterface;
import java.util.List;

//rename to EmployeeServiceImpl
public class EmployeeService implements EmployeeInterface {
    private final EmployeeRepo employeeRepo;

    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public void addTotalTime(Employee employee, Task task, int totalTime) {
        //change statuses to enum
        //clean the conditions
        if (employee != null && task != null && employee.getUserType().equals("Employee")) {
            if (task.getStatus().equals("IN_PROGRESS") || task.getStatus().equals("IN_REVIEW")) {
                task.setTotalTime(totalTime);
            }
        }
    }
    public List<Employee> getAllEmployees() {

        return employeeRepo.getEmployees();
    }


}
