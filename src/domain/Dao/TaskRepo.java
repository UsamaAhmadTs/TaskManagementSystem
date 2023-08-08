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

//public interface TaskRepo {
//    Task createTask(String title, String description, Manager createdBy);
//
//    void assignTaskToEmployee(Task task, Employee assignee);
//
//    void moveTaskToInProgress(Task task);
//
//    void moveTaskToInReview(Task task);
//
//    void moveTaskToCompleted(Task task);
//
//    void addCommentToTask(Task task, Comment comment);
//
//    List<Task> viewTasksByStatus(String status);
//
//    List<Task> viewTasksByEmployee(Employee employee);
//
//    List<Task> viewTasksCreatedByManager(Manager manager);
//
//    List<Task> viewTasksByStatusAndManager(String status, Manager manager);
//
//    List<Task> viewTasksByStatusAndEmployee(String status, Employee employee);
//
//    List<Task> viewAllTasks();
//
//    List<Task> viewTasksBySupervisor(Supervisor supervisor);
//
//    void archiveTask(Task task);
//
//    List<Comment> viewTaskComments(Task task);
//
//    List<Task> viewTaskHistory(Task task);
//
//    List<Employee> viewEmployeesByUserType(String userType);
//
//    void createUser(UserRole userType, String username, String password);
//}

