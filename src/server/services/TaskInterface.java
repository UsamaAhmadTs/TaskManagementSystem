package server.services;

import server.entities.Employee;
import server.entities.Manager;
import server.entities.Task;
import server.entities.User;
import server.services.*;
import server.*;


import java.util.List;

public interface TaskInterface {

    void assignTask(String title, String fullName, Manager manager);

    List<Task> getAllTasks();
    List<Task> viewAllTasksCreatedByManager(Manager manager, String employeeName);
    List<Task> viewAllTasksByUser(String employeeName);

    List<Task> viewTasksByStatus(Employee employee);

    List<Task> viewAllTasksByStatus();

    List<Task> viewTasksByUser(String userRole);

   // void changeTaskStatus(String task, Task.Status status, User person);

    //List<Task> viewAllTasksCreatedByManager(Manager activeManager, Task.Status status);

    Task getTaskByTitle(String title);

    void archiveTask(String title);

    List<Task> viewAssignedTasks(Employee employee);

   // List<Task> viewAllTasksCreatedByManager(Manager activeManager, Task.Status status, String employeeName);

    void createTask(Manager activeManager, String title, String description, double total_time);

}
