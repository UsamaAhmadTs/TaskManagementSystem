package server.services;

        import server.entities.*;

        import java.util.List;
    public interface ManagerService {
        List<Task> viewTasksCreatedByManager(Manager manager);
        List<Manager> getManagers();

        Manager findManager(String manager_username, String manager_password);
    }
