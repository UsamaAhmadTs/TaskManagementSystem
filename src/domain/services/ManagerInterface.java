package domain.services;

        import domain.entities.*;
        import java.util.Date;
        import java.util.List;
    public interface ManagerInterface {

    List<Task> viewTasksCreatedByManager(Manager manager);
        List<Manager> getManagers();

}
