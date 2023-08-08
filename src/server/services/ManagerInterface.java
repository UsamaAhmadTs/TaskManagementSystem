package server.services;

        import server.entities.*;

        import java.util.List;
    public interface ManagerInterface {
        List<Task> viewTasksCreatedByManager(Manager manager);
        List<Manager> getManagers();

}
