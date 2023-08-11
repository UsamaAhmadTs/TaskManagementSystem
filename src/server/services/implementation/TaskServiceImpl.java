package server.services.implementation;

import server.dao.EmployeeRepo;
import server.dao.ManagerRepo;
import server.dao.TaskHistoryRepo;
import server.dao.TaskRepo;
import server.entities.*;
import server.services.EmployeeService;
import server.services.ManagerService;
import server.services.TaskService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaskServiceImpl implements TaskService {

    // Other dependencies and constructors
    private final EmployeeService employeeService;
    private final ManagerService managerServiceImpl;
    private final TaskRepo taskRepo;
    private final EmployeeRepo employeeRepo;
    private final ManagerRepo managerRepo;
    private final TaskHistoryRepo taskHistoryRepo;
    private static TaskServiceImpl instance;

    public TaskServiceImpl(EmployeeService employeeService, ManagerService managerService, TaskRepo taskRepo, EmployeeRepo employeeRepo, ManagerRepo managerRepo, TaskHistoryRepo taskHistoryRepo) {
        this.employeeService = employeeService;
        this.managerServiceImpl = managerService;
        this.taskRepo = taskRepo;
        this.employeeRepo = employeeRepo;
        this.managerRepo = managerRepo;
        this.taskHistoryRepo = taskHistoryRepo;
    }

    public static TaskServiceImpl getInstance(EmployeeServiceImpl employeeService, ManagerServiceImpl managerService, TaskRepo taskRepo, EmployeeRepo employeeRepo, ManagerRepo managerRepo, TaskHistoryRepo taskHistoryRepo) {
        if (instance == null) {
            instance = new TaskServiceImpl(employeeService, managerService, taskRepo, employeeRepo, managerRepo, taskHistoryRepo);
        }
        return instance;
    }

    public void printAllTasks(List<Task> tasks) {
        System.out.println("All Tasks:");
        for (Task task : tasks) {
            System.out.println("Title: " + task.getTitle());
            System.out.println("Description: " + task.getDescription());
            System.out.println("Total Time: " + task.getTotalTime());
            System.out.println("Created By: " + task.getCreatedBy());
            System.out.println("Created At: " + task.getCreatedAt());
            System.out.println("Status: " + task.getStatus());
            System.out.println("Assignee: " + task.getAssignee());
            System.out.println();
        }
    }

    @Override
    public List<Task> getAllTasks() {

        List<Task> tasks = taskRepo.getAllTask();

        return tasks;
    }

//    public Task changeTaskStatus(String taskTitle, Task.Status status, Employee employee) {
//        Task taskToUpdate = getTaskByTitle(taskTitle);
//        taskToUpdate.setStatus(status);
//
//        return taskToUpdate;
//    }

    public Task changeTaskStatus(String taskTitle, Task.Status newStatus, Employee employee) {

            Task taskToUpdate = getTaskByTitle(taskTitle);
        if (employee != null) {
            if (newStatus == Task.Status.IN_REVIEW || newStatus == Task.Status.COMPLETED) {
                if (taskToUpdate.getStatus().equals(Task.Status.IN_PROGRESS.toString())) {
                    taskToUpdate.setStatus(newStatus);
                    addTaskHistory(taskToUpdate, taskToUpdate.getStatus().toString(), newStatus.toString(), employee.getUsername());
                    System.out.println("Task status updated by the employee.");
                } else {
                    System.out.println("Task must be in progress for an Employee to update its status.");
                }
            } else {
                System.out.println("Invalid new status input for an Employee.");
            }
        } else {
            System.out.println("Employee object is null. Cannot update task status.");
        }
//            if (taskToUpdate == null) {
//                System.out.println("Task with the given title was not found.");
//                return taskToUpdate;
//            }
//
//            if (newStatus == Task.Status.IN_REVIEW || newStatus == Task.Status.COMPLETED) {
//
//                taskToUpdate.setStatus(newStatus);
//                addTaskHistory(taskToUpdate, taskToUpdate.getStatus().toString(), newStatus.toString(), employee.getUsername());
//                System.out.println("Task status updated by the employee.");
//
//            }
        return taskToUpdate;
    }

//    @Override
//    public List<Task> viewAllTasksCreatedByManager(Manager manager, String employeeName) {
//        Employee employee = (Employee) employeeRepo.getEmployeesByName(employeeName);
//        if (employee != null) {
//            List<Task> taskbyEmployees = new ArrayList<>();
//            List<Task> allTasks = taskRepo.getAllTask();
//
//            for (Task task : allTasks) {
//                String assigneeName = "N/A"; // Default value in case assignee is null
//                if (task.getAssignee() != null) {
//                    assigneeName = task.getAssignee();
//                }
//
//                String creatorUserName = task.getCreatedBy();
//                String creatorName = task.getCreatedBy();
//
//                if (manager.getUsername().equals(creatorUserName) && employee.getUsername().equals(assigneeName)) {
//                    Instant timestamp = Instant.parse(task.getCreatedAt());
//                    LocalDateTime localDateTime = timestamp.atZone(ZoneId.systemDefault()).toLocalDateTime();
//
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                    String formattedDateTime = localDateTime.format(formatter);
//
//                    Task taskbyEmployee = new Task();
//                    taskbyEmployee.setTitle(task.getTitle());
//                    taskbyEmployee.setDescription(task.getDescription());
//                    taskbyEmployee.setAssignee(assigneeName);
//                    taskbyEmployee.setCreatedAt(formattedDateTime);
//                    taskbyEmployee.setCreatedBy(creatorName);
//
//                    taskbyEmployees.add(taskbyEmployee);
//                }
//            }
//            return taskbyEmployees;
//        } else {
//            return null;
//        }
//    }
    @Override
    public void viewAllTasksCreatedByManager(Manager manager) {
        List<Task> tasksCreatedByManager = new ArrayList<>();
        for (Task task : taskRepo.getAllTask()) {
            if (task.getCreatedBy().equals(manager)) {
                tasksCreatedByManager.add(task);
            }
        }

        if (tasksCreatedByManager.isEmpty()) {
            System.out.println("No tasks created by this manager.");
            return;
        }

        System.out.println("Tasks created by " + manager.getUsername() + ":");
        for (Task task : tasksCreatedByManager) {
            System.out.println(task.getTitle());
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
        System.out.println("Task Created:");
        System.out.println("Title: " + task.getTitle());
        System.out.println("Description: " + task.getDescription());
        System.out.println("Total Time: " + task.getTotalTime());
        System.out.println("Created By: " + task.getCreatedBy());
        System.out.println("Created At: " + task.getCreatedAt());

    }

    public List<Task> viewAssignedTasks(Employee employee) {
        List<Task> assignedTasks = new ArrayList<>();

        if (employee != null && employee.getUserType().equals("Employee")) {
            System.out.println("employee not null");
            for (Task task : taskRepo.getAllTask()) {
                if (task.getAssignee() != null ) {
                    assignedTasks.add(task);
                }
            }
        }
        return assignedTasks;
    }
//    @Override
//    public void assignTask(String title, String employeeName) {
//        Task task = getTaskByTitle(title);
//
//        if (task == null) {
//            System.out.println("Task not found");
//        }
//
//        if (task.isAssigned()) {
//            System.out.println("Task AlreadyAssigned");
//        } else {
//            User assignee = (User) employeeRepo.getEmployeesByName(employeeName);
//            if (assignee == null) {
//                System.out.println("Employee Not Found");
//            }
//
//            task.setAssignee(String.valueOf(assignee));
//            task.setAssigned(true);
//        }
//    }
@Override
public void assignTask(Manager manager, Task task, Employee employee) {
    if (task.isAssigned()) {
        System.out.println("Task Already Assigned");
        return;
    }

    if (employee.getUserType().equals("Employee")) {
        System.out.println("Cannot assign task to a manager.");
        return;
    }

    task.setAssignee(employee.getUsername());
    task.setAssigned(true);
}



    public List<Task> viewTasksByStatus(String status) {
        List<Task> tasksWithStatus = new ArrayList<>();
        for (Task task : taskRepo.getAllTask()) {
            if (task.getStatus().equals(status)) {
                tasksWithStatus.add(task);
            }
            System.out.println("Title: " + task.getTitle());
        }
        return tasksWithStatus;
    }

    @Override
    public Task getTaskByTitle(String title) {
        for (Task task : taskRepo.getAllTask()) {
            if (title.equalsIgnoreCase(task.getTitle())) {
                return task;
            }
        }
        return null; // Task not found
    }

    private void addTaskHistory(Task task, String oldStatus, String newStatus, String username) {
        // Create a new TaskHistory object and add it to the task's history list
        TaskHistory taskHistory = new TaskHistory(Instant.now(), oldStatus, newStatus, username);
        task.addTaskHistory(taskHistory);
    }
    @Override
    public void archiveTask(String title) {
        Task task = getTaskByTitle(title);
        if (task != null) {
            task.setAssigned(false);
            System.out.println("Task archived: " + task.getTitle());
        } else {
            System.out.println("Task with title '" + title + "' not found.");
        }
    }

        //                    public void archiveTask(String title) {
//                        Task task = getTaskByTitle(title);
//                        if (task != null) {
//                            task.setArchived(true);
//                            System.out.println("Task archived: " + task.getTitle());
//                        } else {
//                            System.out.println("Task with title '" + title + "' not found.");
//                        }
//                    }



}
