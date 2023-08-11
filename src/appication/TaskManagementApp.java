package appication;
import server.config.ServerConfig;
import server.entities.*;
import server.services.*;
import server.services.EmployeeService;
import server.services.ManagerService;
import server.services.SupervisorService;
import server.services.TaskService;
import java.util.List;
import java.util.Scanner;

public class TaskManagementApp {
    private static Employee employee;
    private static TaskService taskService = ServerConfig.getTaskService();
    private static ManagerService managerService = ServerConfig.getManagerService();
    private static TaskHistoryService taskHistoryService = ServerConfig.getTaskHistoryService();
    private static EmployeeService employeeService = ServerConfig.getEmployeeService();
    private static UserService userService = ServerConfig.getUserService();

    private static SupervisorService supervisorService = ServerConfig.getSupervisorService();
    private static final int ROLE_EMPLOYEE = 1;
    private static final int ROLE_MANAGER = 2;
    private static final int ROLE_SUPERVISOR = 3;
    private static final int EXIT = 4;


    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nChoose a role:");
            System.out.println("1. Employee");
            System.out.println("2. Manager");
            System.out.println("3. Supervisor");
            System.out.println("4. Exit");
            int input = Integer.parseInt(scanner.nextLine());

            switch (input) {
                case ROLE_EMPLOYEE:
                    handleEmployeeRole(scanner, employeeService);
                    break;
                case ROLE_MANAGER:
                    handleManagerRole(scanner, managerService);
                    break;
                case ROLE_SUPERVISOR:
                    handleSupervisorRole(scanner, supervisorService);
                    break;
                case EXIT:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid input");
            }
        }
    }

    private static void handleEmployeeRole(Scanner scanner, EmployeeService employeeService) {
        System.out.println("Please Enter the Credentials:");

        String employee_name, employee_pass;
        Employee authenticateEmployee;
        do {
            System.out.print("Username: ");
            employee_name = scanner.nextLine();
            System.out.print("Password: ");
            employee_pass = scanner.nextLine();

            authenticateEmployee = employeeService.findEmployee(employee_name, employee_pass);

            if (authenticateEmployee != null) {
                System.out.println("Credentials verified. Access granted!");
                break;
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        } while (true);


        boolean exitMenu = false;

        while (!exitMenu) {
            System.out.println("Please select the task that you would like to do:");
            System.out.println("1: Change task status");
            System.out.println("2: Add total time before moving it to IN_PROGRESS to IN_REVIEW");
            System.out.println("3: View all assigned tasks");
            System.out.println("4: Return");

            int input_employee = Integer.parseInt(scanner.nextLine());

            switch (input_employee) {
                case 1:
                        System.out.print("Enter the title of the task to change status: ");
                        String taskTitleToChangeStatus = scanner.nextLine();

                        System.out.println("To which status do you want to change:");
                        System.out.println("1: IN_PROGRESS");
                        System.out.println("2: IN_REVIEW");

                        int number = Integer.parseInt(scanner.nextLine());

                        Task.Status newStatus;
                        if (number == 1) {
                            newStatus = Task.Status.IN_PROGRESS;
                        } else if (number == 2) {
                            newStatus = Task.Status.IN_REVIEW;
                        } else {
                            System.out.println("Invalid Input");
                            break;
                        }

                        Task updatedTask = taskService.changeTaskStatus(taskTitleToChangeStatus, newStatus, employee);

                        if (updatedTask != null) {
                            System.out.println("Task status changed to: " + newStatus);
                        } else {
                            System.out.println("Task with the given title was not found.");
                        }

                case 2:
                    System.out.print("Enter the title of the task: ");
                    String taskTitle = scanner.nextLine();


                    Task taskToModify = taskService.getTaskByTitle(taskTitle);
                    if (taskToModify != null) {
                        System.out.print("Enter the new status (IN_PROGRESS or IN_REVIEW): ");
                        String newStatusInput = scanner.nextLine();

                        if (newStatusInput.equals("IN_PROGRESS") || newStatusInput.equals("IN_REVIEW")) {
                            Task.Status newStatuss = Task.Status.valueOf(newStatusInput);
                            taskToModify.setStatus(newStatuss);

                            System.out.print("Enter the total time: ");
                            int totalTime = Integer.parseInt(scanner.nextLine());

                            employeeService.addTotalTime(employee, taskToModify, totalTime);

                            System.out.println("Total time added and status updated.");
                        } else {
                            System.out.println("Invalid new status input.");
                        }
                    } else {
                        System.out.println("Task with the given title was not found.");
                    }
                    break;
                case 3:
                    taskService.viewAssignedTasks(employee);
                    break;
                case 4: exitMenu = true;
            }

        }
    }

    private static void handleManagerRole(Scanner scanner, ManagerService managerService) {
        System.out.println("Hey Manager!!");
        System.out.println("Please Enter the Credentials:");

        String manager_username;
        String manager_password;
        Manager authenticatedManager;


        do {
            System.out.print("Username: ");
            manager_username = scanner.nextLine();
            System.out.print("Password: ");
            manager_password = scanner.nextLine();
            authenticatedManager = managerService.findManager(manager_username, manager_password);

            if (authenticatedManager != null) {
                System.out.println("Credentials verified. Access granted!");
                // Perform actions for authenticatedEmployee here
                break;
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        } while (true);

        boolean exitMenuManager = false;

        while (!exitMenuManager) {
            System.out.println("Please select the task that you would like to do:");
            System.out.println("1: Create Task");
            System.out.println("2: Assign task to any employee");
            System.out.println("3: View all tasks created by Manager");
            System.out.println("4: Return");

            int input_manager = Integer.parseInt(scanner.nextLine());
            switch (input_manager) {
                case 1 -> {
                    System.out.print("Please enter title for task to create.\n");
                    String title = scanner.nextLine();
                    System.out.print("Please enter description for task.\n");
                    String description = scanner.nextLine();
                    taskService.createTask(authenticatedManager, title, description, 1);
                }
                case 2 -> {
                    System.out.print("Enter the title of the task: ");
                    String taskTitleToAssign = scanner.nextLine();

                    System.out.print("Enter the name of the employee to whom the task will be assigned: ");
                    String employeeName = scanner.nextLine();
                    System.out.print("Enter the Password of the employee to whom the task will be assigned: ");
                    String employeePass= scanner.nextLine();

                    Task taskToAssign = taskService.getTaskByTitle(taskTitleToAssign);
                    Employee employeeToAssign = employeeService.findEmployee(employeeName, employeePass);

                    if (taskToAssign != null && employeeToAssign != null) {
                        taskService.assignTask(authenticatedManager, taskToAssign, employeeToAssign);
                        System.out.println("Task assigned to the employee.");
                    } else {
                        System.out.println("Task or employee with the given details was not found.");
                    }
                }
                case 3 -> {
                    System.out.println("Tasks created by Manager:");
                    managerService.viewTasksCreatedByManager(authenticatedManager);
                }
                case 4 -> exitMenuManager = true;
                default -> System.out.println("Invalid Input");
            }
        }


    }

    private static void handleSupervisorRole(Scanner scanner, SupervisorService supervisorService) {
        System.out.println("Hey Supervisor!!");
        System.out.println("Please Enter the Credentials:");
        System.out.println("supervisorService: " + supervisorService);

        Supervisor authenticatedSupervisor = null;

        do {
            System.out.print("Username: ");
            String supervisorUsername = scanner.nextLine();
            System.out.print("Password: ");
            String supervisorPassword = scanner.nextLine();

            authenticatedSupervisor = supervisorService.verifyCredentials(supervisorUsername, supervisorPassword);

            if (authenticatedSupervisor != null) {
                System.out.println("Credentials verified. Access granted!");
                // Perform actions for authenticatedSupervisor here
                break;
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        } while (true);

        boolean exitMenuSupervisor = false;

        while (!exitMenuSupervisor) {
            System.out.println("Please select the task that you would like to do:");
            System.out.println("1: View all tasks");
            System.out.println("2: View all tasks by status");
            System.out.println("3: Archive Task");
            System.out.println("4: View Task History");
            System.out.println("5: View all Employees by User role");
            System.out.println("6: Create User");

            System.out.println("7: Return");

            int input_supervisor = Integer.parseInt(scanner.nextLine());

            switch (input_supervisor) {
                case 1 -> {
                    taskService.getAllTasks();
                    taskService.printAllTasks(taskService.getAllTasks());
                }
                case 2 -> {
                    System.out.print("Enter the status to view tasks (IN_PROGRESS or IN_REVIEW or CREATED): ");
                    String status = scanner.nextLine();
                    taskService.viewTasksByStatus(status);}

                case 3 -> {
                    System.out.print("Enter the title of the task to archive: ");
                    String taskTitleToArchive = scanner.nextLine();
                    taskService.archiveTask(taskTitleToArchive);

                }
                case 4 ->{
                    System.out.print("Enter the title of the task to view history: ");
                    String taskTitleToViewHistory = scanner.nextLine();

                    List<TaskHistory> taskHistoryList = taskHistoryService.viewTaskHistory(taskTitleToViewHistory);
                    for (TaskHistory history : taskHistoryList) {
                        System.out.println(history);
                    }
                }
                case 5 -> {
                    System.out.println("1: To view Employees");
                    System.out.println("2: To view Managers");
                    int number = Integer.parseInt(scanner.nextLine());

                    if (number == 1) {
                        employeeService.getAllEmployees();
                        employeeService.printAllEmployees(employeeService.getAllEmployees());

                    } else if (number == 2) {
                        managerService.getManagers();
                    } else {

                        System.out.println("Invalid Input");
                    }
                }
                case 6 -> {
                    System.out.println("1: To Create Employee");
                    System.out.println("2: To Create Manager");

                    int parseInt = Integer.parseInt(scanner.nextLine());
                    if (parseInt == 1) {
                        System.out.println("Please enter the UserName:");
                        String userName = scanner.nextLine();
                        System.out.println("Please enter the Password:");
                        String password = scanner.nextLine();
                        userService.createUser("Employee", userName, password);
                        if(userService!=null) {
                            System.out.println("user Created");
                        }
                    } else if (parseInt == 2) {
                        System.out.println("Please enter the UserName:");
                        String userName = scanner.nextLine();
                        System.out.println("Please enter the Password:");
                        String password = scanner.nextLine();
                        userService.createUser("Manager", userName, password);

                    } else {

                        System.out.println("Invalid Input");
                    }
                }
                case 7 -> exitMenuSupervisor = true; // Set the flag to true to exit the loop
                default -> System.out.println("Invalid Input");
            }
        }

    }
}
