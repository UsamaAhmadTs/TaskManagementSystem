package domain.Dao.implementation;

import domain.Dao.ManagerRepo;
import domain.entities.Manager;
import domain.entities.User;

import java.util.ArrayList;
import java.util.List;

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
            if (manager.getUsername().equals(name)) {
                managersByName.add(manager);
            }
        }
        return managersByName;
    }

    @Override
    public Manager findManager(String username) {
        for (Manager manager : managers) {
            if (manager.getUsername().equals(username)) {
                return manager;
            }
        }
        return null;
    }
}
