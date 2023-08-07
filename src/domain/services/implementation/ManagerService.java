// ManagerService.java
package domain.services.implementation;

import domain.entities.*;
import domain.services.ManagerInterface;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManagerService implements ManagerInterface {
    private List<Task> tasks;  // Assuming you manage tasks here
    private int taskIdCounter = 1;
    public ManagerService(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void createTask(Manager manager, String title, String description, Date createdAt) {
        if (manager != null) {
            Task task = new Task(generateTaskId(), title, description, manager, createdAt);
            tasks.add(task);
        }
    }

    public void assignTask(Manager manager, Task task, Employee employee) {
        if (manager != null && task != null && employee != null && !employee.getUserType().equals("Manager")) {
            if (task.getCreatedBy().equals(manager)) {
                task.setAssignee(employee);
            }
        }
    }

    public void moveTaskToCompleted(Manager manager, Task task) {
        if (manager != null && task != null && task.getStatus().equals("IN_REVIEW")) {
            task.setStatus("COMPLETED", manager);
        }
    }

    public List<Task> viewTasksCreatedByManager(Manager manager) {
        List<Task> tasksCreatedByManager = new ArrayList<>();
        if (manager != null) {
            for (Task task : tasks) {
                if (task.getCreatedBy() != null && task.getCreatedBy().equals(manager)) {
                    tasksCreatedByManager.add(task);
                }
            }
        }
        return tasksCreatedByManager;
    }

    public List<Task> viewTasksByEmployee(Employee employee) {
        List<Task> tasksByEmployee = new ArrayList<>();
        if (employee != null) {
            for (Task task : tasks) {
                if (task.getAssignee() != null && task.getAssignee().equals(employee)) {
                    tasksByEmployee.add(task);
                }
            }
        }
        return tasksByEmployee;
    }

    public List<Task> viewTasksByStatus(String status) {
        List<Task> tasksWithStatus = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getStatus().equals(status)) {
                tasksWithStatus.add(task);
            }
        }
        return tasksWithStatus;
    }

    public List<Task> viewTasksByEmployeeAndStatus(Employee employee, String status) {
        List<Task> tasksByEmployeeAndStatus = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getAssignee() != null && task.getAssignee().equals(employee) && task.getStatus().equals(status)) {
                tasksByEmployeeAndStatus.add(task);
            }
        }
        return tasksByEmployeeAndStatus;
    }
    private int generateTaskId() {
        int newTaskId = taskIdCounter;
        taskIdCounter++;  // Increment the counter for the next task
        return newTaskId;
    }
}
