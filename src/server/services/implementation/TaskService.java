package server.services.implementation;

import  server.entities.*;
import  server.entities.User;
import server.dao.EmployeeRepo;
import server.dao.ManagerRepo;
import server.dao.TaskHistoryRepo;
import server.dao.TaskRepo;
import server.entities.Employee;
import server.entities.Manager;
import server.entities.Task;
import server.services.TaskInterface;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaskService implements TaskInterface {

    // Other dependencies and constructors
    private final EmployeeService employeeService;
    private final ManagerService managerService;
    private final TaskRepo taskRepo;
    private final EmployeeRepo employeeRepo;

    private final ManagerRepo managerRepo;
    private final TaskHistoryRepo taskHistoryRepo;

    public TaskService(EmployeeService employeeService, ManagerService managerService, TaskRepo taskRepo, EmployeeRepo employeeRepo, ManagerRepo managerRepo, TaskHistoryRepo taskHistoryRepo) {
        this.employeeService = employeeService;
        this.managerService = managerService;
        this.taskRepo = taskRepo;
        this.employeeRepo = employeeRepo;
        this.managerRepo = managerRepo;
        this.taskHistoryRepo = taskHistoryRepo;
    }

    @Override
    public List<Task> getAllTasks() {

        List<Task> getTasks = taskRepo.getAllTask();

        return getTasks;
    }

    @Override
    public List<Task> viewAllTasksCreatedByManager(Manager manager, String employeeName) {
        Employee employee = (Employee) employeeRepo.getEmployeesByName(employeeName);
        if (employee != null) {
            List<Task> taskbyEmployees = new ArrayList<>();
            List<Task> allTasks = taskRepo.getAllTask();

            for (Task task : allTasks) {
                String assigneeName = "N/A"; // Default value in case assignee is null
                if (task.getAssignee() != null) {
                    assigneeName = task.getAssignee();
                }

                String creatorUserName = task.getCreatedBy();
                String creatorName = task.getCreatedBy();

                if (manager.getUsername().equals(creatorUserName) && employee.getUsername().equals(assigneeName)) {
                    Instant timestamp = Instant.parse(task.getCreatedAt());
                    LocalDateTime localDateTime = timestamp.atZone(ZoneId.systemDefault()).toLocalDateTime();

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String formattedDateTime = localDateTime.format(formatter);

                    Task taskbyEmployee = new Task();
                    taskbyEmployee.setTitle(task.getTitle());
                    taskbyEmployee.setDescription(task.getDescription());
                    taskbyEmployee.setAssignee(assigneeName);
                    taskbyEmployee.setCreatedAt(formattedDateTime);
                    taskbyEmployee.setCreatedBy(creatorName);

                    taskbyEmployees.add(taskbyEmployee);
                }
            }
            return taskbyEmployees;
        } else {
            return null;
        }
    }

    public void assignTask(Manager manager, Task task, Employee employee) {
        if (manager != null && task != null && employee != null && !employee.getUserType().equals("Manager")) {
                task.setAssignee(String.valueOf(employee));
        }
    }
    @Override
    public void createTask(Manager activeManager, String title, String description, int total_time) {

        Task task = new Task(title, description, total_time);
        task.setCreatedBy(activeManager.getUsername()); // Set the username of the active manager
        Instant currentInstant = Instant.now();
        String createdAt = currentInstant.toString();
        task.setCreatedAt(createdAt); // Store the provided formatted timestamp

        for (Task taskInRepo : taskRepo.getAllTask()) {
            if (taskInRepo.getTitle().equals(task.getTitle())) {
                throw new IllegalArgumentException("Task with the same title already exists.");
            }
        }
        taskRepo.addTask(task);
    }

    public List<Task> viewAssignedTasks(Employee employee) {
        List<Task> assignedTasks = new ArrayList<>();

        if (employee != null && employee.getUserType().equals("Employee")) {
            for (Task task : taskRepo.getAllTask()) {
                if (task.getAssignee() != null ) {
                    assignedTasks.add(task);
                }
            }
        }
        return assignedTasks;
    }

    public List<Task> viewTasksByStatus(String status) {
        List<Task> tasksWithStatus = new ArrayList<>();
        for (Task task : taskRepo.getAllTask()) {
            if (task.getStatus().equals(status)) {
                tasksWithStatus.add(task);
            }
        }
        return tasksWithStatus;
    }

    @Override
    public Task getTaskByTitle(String title) {
        if (title != null) {
            for (Task task : taskRepo.getAllTask()) {
                if (task != null && task.getTitle() != null && task.getTitle().equalsIgnoreCase(title)) {
                    return task;
                }
            }
        }
        return null; // Task not found
    }

    @Override
    public void archiveTask(String title) {
        Task task = getTaskByTitle(title);
        task.setAssigned(false);
        task.setAssignee(null);
    }


}
