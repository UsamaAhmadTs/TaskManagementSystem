package domain.services;

        import domain.entities.*;
        import java.util.Date;
        import java.util.List;
    public interface ManagerInterface {

    void createTask(Manager manager, String title, String description, Date createdAt);

    void assignTask(Manager manager, Task task, Employee employee);

    void moveTaskToCompleted(Manager manager, Task task);

    List<Task> viewTasksCreatedByManager(Manager manager);

    List<Task> viewTasksByEmployee(Employee employee);

    List<Task> viewTasksByStatus(String status);

    List<Task> viewTasksByEmployeeAndStatus(Employee employee, String status);
}
