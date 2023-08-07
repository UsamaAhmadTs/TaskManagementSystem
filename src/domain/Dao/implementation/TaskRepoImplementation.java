package domain.Dao.implementation;

import domain.Dao.TaskRepo;
import domain.entities.Comment;
import domain.entities.Employee;
import domain.entities.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepoImplementation implements TaskRepo {
    List<Task> tasks= new ArrayList<>();
    public void changeTaskStatus( Task task, String newStatus, Employee employee) {
        if (employee != null && task != null && employee.getUserType().equals("Employee")) {
            if (newStatus.equals("IN_PROGRESS") || newStatus.equals("IN_REVIEW")) {
                task.setStatus(newStatus, employee);
            }
        }
    }

    public void addTotalTime(Employee employee, Task task, int totalTime) {
        if (employee != null && task != null && employee.getUserType().equals("Employee")) {
            if (task.getStatus().equals("IN_PROGRESS") || task.getStatus().equals("IN_REVIEW")) {
                task.setTotalTime(totalTime);
            }
        }
    }

    public void addComment(Employee employee, Task task, Comment comment) {
        if (employee != null && task != null && employee.getUserType().equals("Employee")) {
            if (task.getStatus().equals("IN_PROGRESS") || task.getStatus().equals("IN_REVIEW")) {
                task.getComments().add(comment);
            }
        }
    }

    public List<Task> viewAssignedTasks(Employee employee) {
        List<Task> assignedTasks = new ArrayList<>();
        if (employee != null && employee.getUserType().equals("Employee")) {
            for (Task task : tasks) {
                if (task.getAssignee() != null && task.getAssignee().equals(employee)) {
                    assignedTasks.add(task);
                }
            }
        }
        return assignedTasks;
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
}
