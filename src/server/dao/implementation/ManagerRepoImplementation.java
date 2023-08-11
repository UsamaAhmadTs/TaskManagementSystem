package server.dao.implementation;

import server.dao.ManagerRepo;
import server.entities.Employee;
import server.entities.Manager;
import server.entities.User;

import java.util.ArrayList;
import java.util.List;

public class ManagerRepoImplementation implements ManagerRepo {
    private List<Manager> managers = new ArrayList<>(List.of(new Manager("usama", "1")));

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
            if (manager.getUsername().equals(name)) {
                managersByName.add(manager);
            }
        }
        return managersByName;
    }

    @Override
    public Manager findManager(String username, String password) {
        for (Manager manager : managers) {
            if (manager.getUsername().equals(username) && manager.getPassword().equals(password)) {
                return manager;
            }
        }
        return null;
    }
}
