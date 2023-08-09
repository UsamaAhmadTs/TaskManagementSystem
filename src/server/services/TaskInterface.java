package server.services;

import server.entities.Employee;
import server.entities.Manager;
import server.entities.Task;
import server.entities.User;
import server.services.*;
import server.*;


import java.util.List;

public interface TaskInterface {

    void assignTask(Manager manager, Task task, Employee employee);

    List<Task> getAllTasks();
    List<Task> viewAllTasksCreatedByManager(Manager manager, String employeeName);

    List<Task> viewTasksByStatus(String status);

    Task getTaskByTitle(String title);

    void archiveTask(String title);

    List<Task> viewAssignedTasks(Employee employee);

    void createTask(Manager activeManager, String title, String description, int total_time);

}
