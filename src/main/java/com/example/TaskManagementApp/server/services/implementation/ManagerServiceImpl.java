 //ManagerService.java
package com.example.TaskManagementApp.server.services.implementation;

 import com.example.TaskManagementApp.server.dao.ManagerRepo;
 import com.example.TaskManagementApp.server.dao.TaskRepo;
 import com.example.TaskManagementApp.server.entities.Employee;
 import com.example.TaskManagementApp.server.entities.Manager;
 import com.example.TaskManagementApp.server.entities.Task;
 import com.example.TaskManagementApp.server.services.ManagerService;
 import org.springframework.stereotype.Service;

 import java.util.ArrayList;
 import java.util.List;
 @Service
public class ManagerServiceImpl implements ManagerService {
    private final ManagerRepo managerRepo;
    private final TaskRepo taskRepo;

    public ManagerServiceImpl(ManagerRepo managerRepo, TaskRepo taskRepo) {
        this.managerRepo = managerRepo;
        this.taskRepo = taskRepo;
    }

//    public List<Task> viewTasksCreatedByManager(Manager manager) {
//        List<Task> tasksCreatedByManager = new ArrayList<>();
//        if (manager != null) {
//            List<Task> allTasks = taskRepo.findAll();
//            for (Task task : allTasks) {
//                if (task.getCreatedBy() != null)  {
//                    tasksCreatedByManager.add(task);
//                }
//            }
//        }
//        return tasksCreatedByManager;
//    }
//    public List<Manager> getManagers() {
//        List<Manager> managers = managerRepo.findAll();
//        return managers;
//    }
//    public Manager findManager(String username) {
//        return managerRepo.getManagersByUserName(username);
//    }
}
