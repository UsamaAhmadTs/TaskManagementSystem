
package domain.Dao;
import domain.entities.Employee;
import domain.entities.User;
import java.util.List;

public interface EmployeeRepo {
    List<Employee> getEmployees();

    void addEmployee(Employee employee);

    Employee createEmployee(String username, String password);

    List<User> getEmployeesByName(String name);

    Employee findEmployee(String username);
}
