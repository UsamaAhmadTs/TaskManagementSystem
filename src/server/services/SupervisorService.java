package server.services;
import server.entities.Supervisor;
import java.util.List;

public interface SupervisorService {
    List<Supervisor> viewSupervisors();
    Supervisor verifyCredentials(String username, String password);

}
