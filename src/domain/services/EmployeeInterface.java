package domain.services;
import java.util.List;
import domain.entities.Employee;
import domain.entities.Task;
import domain.entities.Comment;

public interface EmployeeInterface {

    //void changeTaskStatus(Employee employee, Task task, String newStatus);

    void addTotalTime(Employee employee, Task task, int totalTime);
    List<Employee> getAllEmployees();

//    void addComment(Employee employee, Task task, Comment comment);
//
//    List<Task> viewAssignedTasks(Employee employee);
//
//    List<Task> viewTasksByStatus(String status);
}
