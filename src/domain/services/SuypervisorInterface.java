package domain.services;

import domain.entities.*;
import java.util.List;
public interface SuypervisorInterface {

    List<Task> viewAllTasks();

    List<Task> viewTasksByStatus(String status);

    List<Task> viewTasksByEmployee(Employee employee);

    List<Task> viewTasksByManager(Manager manager);

    void archiveTask(Supervisor supervisor, Task task);

    void addComment(Task task, Comment comment);

    List<TaskHistory> viewTaskHistory(Task task);

    List<Employee> viewEmployeesByUserType(String userType);

    User createUser(int userId, String username, String userType);
}
