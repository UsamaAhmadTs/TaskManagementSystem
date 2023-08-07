// SupervisorService.java
package domain.services.implementation;

import domain.entities.*;
import domain.services.SuypervisorInterface;

import java.util.ArrayList;
import java.util.List;

public class SupervisorService implements SuypervisorInterface {
    private List<Task> tasks;  // Assuming you manage tasks here

    public SupervisorService(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> viewAllTasks() {
        return new ArrayList<>(tasks);  // Return a copy of all tasks
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

    public List<Task> viewTasksByManager(Manager manager) {
        List<Task> tasksByManager = new ArrayList<>();
        if (manager != null) {
            for (Task task : tasks) {
                if (task.getCreatedBy() != null && task.getCreatedBy().equals(manager)) {
                    tasksByManager.add(task);
                }
            }
        }
        return tasksByManager;
    }

    public void archiveTask(Supervisor supervisor, Task task) {
        if (supervisor != null && task != null) {
            task.setAssignee(null);  // Unassign the task
            tasks.remove(task);  // Remove the task from the list
            // You might want to implement additional logic for archiving
        }
    }

    public void addComment(Task task, Comment comment) {
        if (task != null && comment != null) {
            task.getComments().add(comment);
        }
    }

    public List<TaskHistory> viewTaskHistory(Task task) {
        List<TaskHistory> taskHistoryList = new ArrayList<>();
        if (task != null) {
            for (TaskHistory history : task.getTaskHistory()) {
                taskHistoryList.add(history);
            }
        }
        return taskHistoryList;
    }


    public List<Employee> viewEmployeesByUserType(String userType) {
        List<Employee> employeesByUserType = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getAssignee() != null && task.getAssignee().getUserType().equals(userType) && task.getAssignee() instanceof Employee) {
                employeesByUserType.add((Employee) task.getAssignee());
            }
            if (task.getCreatedBy() != null && task.getCreatedBy().getUserType().equals(userType) && task.getCreatedBy() instanceof Employee) {
                employeesByUserType.add((Employee) task.getCreatedBy());
            }
        }
        return employeesByUserType;
    }


    public User createUser(int userId, String username, String userType) {
        if (userType.equals("Employee")) {
            return new Employee(userId, username);
        } else if (userType.equals("Manager")) {
            return new Manager(userId, username);
        } else if (userType.equals("Supervisor")) {
            return new Supervisor(userId, username);
        }
        return null;
    }

}
