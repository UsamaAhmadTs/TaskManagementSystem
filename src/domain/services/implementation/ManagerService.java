// ManagerService.java
//package domain.services.implementation;
//import domain.services.ManagerInterface;
//import domain.Dao.ManagerRepo;
//import domain.entities.Manager;
//import domain.entities.Task;
//import java.util.ArrayList;
//import java.util.List;

//public class ManagerService implements ManagerInterface {
    //private final ManagerRepo ManagerRepo;
    //private final TaskRepo taskRepo;

//    public ManagerService(ManagerRepo managerRepo, TaskRepo taskRepo) {
//        this.managerRepo = managerRepo;
//        this.taskRepo = taskRepo;
//    }

//    public List<Task> viewTasksCreatedByManager(Manager manager) {
//        List<Task> tasksCreatedByManager = new ArrayList<>();
//        if (manager != null) {
//            for (Task task : tasks) {
//                if (task.getCreatedBy() != null && task.getCreatedBy().equals(manager)) {
//                    tasksCreatedByManager.add(task);
//                }
//            }
//        }
//        return tasksCreatedByManager;
//    }
//    public List<Task> viewTasksCreatedByManager(Manager manager) {
//        List<Task> tasksCreatedByManager = new ArrayList<>();
//        if (manager != null) {
//            List<Task> allTasks = taskRepo.getTasks(); // Get tasks from the repository
//            for (Task task : allTasks) {
//                if (task.getCreatedBy() != null && task.getCreatedBy().equals(manager)) {
//                    tasksCreatedByManager.add(task);
//                }
//            }
//        }
//        return tasksCreatedByManager;
//    }
//    public List<Manager> getManagers() {
//
//        //return ManagerRepo.getManagers();
//    }
//}
