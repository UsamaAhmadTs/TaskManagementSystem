package server.config;
import server.dao.implementation.*;
import server.entities.TaskHistory;
import server.services.*;
import server.dao.*;
import server.services.implementation.*;
public class ServerConfig {

    private static TaskService taskService; // Make it static
    private static ManagerService managerService;
    private static EmployeeService employeeService;

    private static TaskHistoryService taskHistoryService;

    private static UserService userService;
    private static SupervisorService supervisorService;
    private static TaskRepo taskRepo = new TaskRepoImplementation();
    private static ManagerRepo managerRepo = new ManagerRepoImplementation();
    private static SupervisorRepo supervisorRepo = new SupervisorRepoImplementation();
    private static EmployeeRepo employeeRepo = new EmployeeRepoImplementation();
    private static TaskHistoryRepo taskHistoryRepo = new TaskHistoryRepoImplementation(taskRepo);
    private static UserRepo userRepo= new UserRepoImplementation();


    public static ManagerService getManagerService() {
        if(managerService==null){
            managerService = new ManagerServiceImpl(managerRepo, taskRepo);
        }
        return managerService;
    }
    public static SupervisorService getSupervisorService() {
        if (supervisorService==null){
            supervisorService = new SupervisorServiceImpl(supervisorRepo);
        }
        return supervisorService;
    }
    public static EmployeeService getEmployeeService() {
        if(employeeService==null){
            employeeService = new EmployeeServiceImpl(employeeRepo);
        }
        return employeeService;
    }
    public static TaskHistoryService getTaskHistoryService(){
        if(taskService==null){
            taskService = new TaskServiceImpl(employeeService, managerService, taskRepo, employeeRepo, managerRepo, taskHistoryRepo);
        }
        return taskHistoryService;
    }
    public static UserService getUserService() {
        if(userService==null){
            userService = new UserServiceImpl(userRepo, managerRepo, employeeRepo);
        }
        return userService;
    }
    public static TaskService getTaskService() {
        if(taskService==null){
            taskService = new TaskServiceImpl(employeeService, managerService, taskRepo, employeeRepo, managerRepo, taskHistoryRepo);
        }
        return taskService;
    }
}
