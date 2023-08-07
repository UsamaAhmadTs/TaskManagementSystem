package domain.Dao;
import domain.entities.*;
import java.util.List;

public interface TaskRepo {
    void changeTaskStatus(Task task, String newStatus, Employee employee);
    void addTotalTime( Employee employee, Task task, int totalTime);
    void addComment(Employee employee, Task task, Comment comment);
    List<Task> viewAssignedTasks(Employee employee);
    List<Task> viewTasksByStatus(String status);
}
