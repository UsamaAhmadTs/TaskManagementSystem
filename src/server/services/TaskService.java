package server.services;

import server.entities.Employee;
import server.entities.Manager;
import server.entities.Task;


import java.util.List;

public interface TaskService {

    void assignTask(Manager manager, Task task, Employee employee);
    Task changeTaskStatus(String task, Task.Status status, Employee employee);

    List<Task> getAllTasks();
    void printAllTasks(List<Task> tasks);
    void viewAllTasksCreatedByManager(Manager manager);

    List<Task> viewTasksByStatus(String status);

    Task getTaskByTitle(String title);

    void archiveTask(String title);

    List<Task> viewAssignedTasks(Employee employee);

    void createTask(Manager activeManager, String title, String description, int total_time);

}
