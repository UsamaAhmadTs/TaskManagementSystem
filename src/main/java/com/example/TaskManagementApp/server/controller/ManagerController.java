package com.example.TaskManagementApp.server.controller;

import com.example.TaskManagementApp.server.entities.Manager;
import com.example.TaskManagementApp.server.entities.Task;
import com.example.TaskManagementApp.server.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/managers")
public class ManagerController {

    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/")
    public List<Manager> getManagers() {
        return managerService.getManagers();
    }

    @GetMapping("/{username}")
    public Manager findManager(@PathVariable String username) {
        return managerService.findManager(username);
    }

//    @GetMapping("/{managerId}/tasks")
//    public List<Task> viewTasksCreatedByManager(@PathVariable Long managerId) {
//        Manager manager = new Manager(); // Get the manager instance using managerId
//        return managerService.viewTasksCreatedByManager(manager);
//    }
}
