// application/TaskManagementApp.java
package appication;

import domain.entities.*;
import domain.services.implementation.SupervisorService;

import java.util.ArrayList;
import java.util.Scanner;

public class TaskManagementApp {
    public static void main(String[] args) {
        AuthenticationService authService = new AuthenticationService();
        SupervisorService supervisorService = new SupervisorService(new ArrayList<>());  // Initialize tasks list

        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (authService.authenticate(username, password)) {
            // User is authenticated, proceed with appropriate service
            User user = getUserByUsername(username);
            if (user instanceof Supervisor) {
                handleSupervisorActions((Supervisor) user, supervisorService);
            } else if (user instanceof Manager) {
                handleManagerActions((Manager) user);
            } else if (user instanceof Employee) {
                handleEmployeeActions((Employee) user);
            }
        } else {
            System.out.println("Authentication failed.");
        }

    }
    private static User getUserByUsername(String username) {
        if ("supervisor".equals(username)) {
            return new Supervisor(1, "Supervisor Name");
        } else if ("manager".equals(username)) {
            return new Manager(2, "Manager Name");
        } else if ("employee".equals(username)) {
            return new Employee(3, "Employee Name");
        }
        return null; // User not found
    }

    private static void handleSupervisorActions(Supervisor supervisor, SupervisorService supervisorService) {
        // Implement supervisor actions using supervisorService
    }

    private static void handleManagerActions(Manager manager) {
        // Implement manager actions
    }

    private static void handleEmployeeActions(Employee employee) {
        // Implement employee actions
    }
    // Define methods to handle actions for each user role

//    private static void handleSupervisorActions(Supervisor supervisor, SupervisorService supervisorService) {
//        Scanner scanner = new Scanner(System.in);
//        int choice;
//
//        do {
//            System.out.println("Supervisor Menu:");
//            System.out.println("1. View all tasks");
//            System.out.println("2. View employees by user type");
//            System.out.println("3. Exit");
//            System.out.print("Enter your choice: ");
//            choice = scanner.nextInt();
//
//            switch (choice) {
//                case 1:
//                    // View all tasks
//                    List<Task> tasks = supervisorService.getAllTasks();
//                    // Implement displaying tasks
//                    break;
//                case 2:
//                    // View employees by user type
//                    System.out.print("Enter user type: ");
//                    String userType = scanner.next();
//                    List<Employee> employees = supervisorService.viewEmployeesByUserType(userType);
//                    // Implement displaying employees
//                    break;
//                case 3:
//                    System.out.println("Exiting...");
//                    break;
//                default:
//                    System.out.println("Invalid choice");
//                    break;
//            }
//        } while (choice != 3);
//    }


//    private static void handleManagerActions(Manager manager) {
//        // Implement manager actions
//    }

//    private static void handleEmployeeActions(Employee employee, EmployeeService employeeService) {
//        Scanner scanner = new Scanner(System.in);
//        int choice;
//
//        do {
//            System.out.println("Employee Menu:");
//            System.out.println("1. Change task status");
//            System.out.println("2. Add total time");
//            System.out.println("3. Add comment to task");
//            System.out.println("4. View all assigned tasks");
//            System.out.println("5. View all tasks by status");
//            System.out.println("6. Exit");
//            System.out.print("Enter your choice: ");
//            choice = scanner.nextInt();
//
//            switch (choice) {
//                case 1:
//                    // Change task status
//                    System.out.print("Enter task ID: ");
//                    int taskId = scanner.nextInt();
//                    System.out.print("Enter new status (IN_PROGRESS or IN_REVIEW): ");
//                    String newStatus = scanner.next();
//                    employeeService.changeTaskStatus(employee, taskId, newStatus);
//                    // Implement changing task status
//                    break;
//                case 2:
//                    // Add total time
//                    System.out.print("Enter task ID: ");
//                    int taskId = scanner.nextInt();
//                    System.out.print("Enter total time: ");
//                    int totalTime = scanner.nextInt();
//                    employeeService.addTotalTime(employee, taskId, totalTime);
//                    // Implement adding total time
//                    break;
//                case 3:
//                    // Add comment to task
//                    System.out.print("Enter task ID: ");
//                    int taskId = scanner.nextInt();
//                    scanner.nextLine(); // Consume newline
//                    System.out.print("Enter comment: ");
//                    String comment = scanner.nextLine();
//                    employeeService.addCommentToTask(employee, taskId, comment);
//                    // Implement adding comment
//                    break;
//                case 4:
//                    // View all assigned tasks
//                    List<Task> assignedTasks = employeeService.viewAllAssignedTasks(employee);
//                    // Implement displaying tasks
//                    break;
//                case 5:
//                    // View all tasks by status
//                    System.out.print("Enter status: ");
//                    String status = scanner.next();
//                    List<Task> tasksByStatus = employeeService.viewTasksByStatus(status);
//                    // Implement displaying tasks
//                    break;
//                case 6:
//                    System.out.println("Exiting...");
//                    break;
//                default:
//                    System.out.println("Invalid choice");
//                    break;
//            }
//        } while (choice != 6);
//    }

}
