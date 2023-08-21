package com.example.TaskManagementApp.server.dao.implementation;

import com.example.TaskManagementApp.server.entities.Manager;
import com.example.TaskManagementApp.server.entities.User;
import com.example.TaskManagementApp.server.dao.ManagerRepo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class ManagerRepoImplementation implements ManagerRepo {
    private List<Manager> managers = new ArrayList<>();

    @Override
    public List<Manager> getManagers() {
        return managers;
    }

    @Override
    public void addManager(Manager manager) {
        managers.add(manager);
    }

    @Override
    public Manager createManager(String username, String password) {
        Manager newManager = new Manager(username, password);
        managers.add(newManager);
        return newManager;
    }

    @Override
    public List<User> getManagersByName(String name) {
        List<User> managersByName = new ArrayList<>();
        for (Manager manager : managers) {
            if (manager.getUserName().equals(name)) {
                managersByName.add(manager);
            }
        }
        return managersByName;
    }

    @Override
    public Manager findManager(String username) {
        for (Manager manager : managers) {
            if (manager.getUserName().equals(username)) {
                return manager;
            }
        }
        return null;
    }
}
