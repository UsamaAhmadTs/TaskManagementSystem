 //ManagerService.java
package server.services.implementation;
import server.services.ManagerInterface;
import server.dao.ManagerRepo;
import server.dao.TaskRepo;
import server.entities.Manager;
import server.entities.Task;
import java.util.ArrayList;
import java.util.List;

public class ManagerService implements ManagerInterface {
    private final ManagerRepo managerRepo;
    private final TaskRepo taskRepo;

    public ManagerService(ManagerRepo managerRepo, TaskRepo taskRepo) {
        this.managerRepo = managerRepo;
        this.taskRepo = taskRepo;
    }

    public List<Task> viewTasksCreatedByManager(Manager manager) {
        List<Task> tasksCreatedByManager = new ArrayList<>();
        if (manager != null) {
            List<Task> allTasks = taskRepo.getAllTask(); // Get tasks from the repository
            for (Task task : allTasks) {
                if (task.getCreatedBy() != null && task.getCreatedBy().equals(manager)) {
                    tasksCreatedByManager.add(task);
                }
            }
        }
        return tasksCreatedByManager;
    }
    public List<Manager> getManagers() {
        for (Manager manager : managerRepo.getManagers()) {
            System.out.println(manager.getUsername());
        }
        return null;
    }
}
