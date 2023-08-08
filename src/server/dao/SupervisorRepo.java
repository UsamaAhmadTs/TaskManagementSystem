package server.dao;

import server.entities.Supervisor;
import java.util.List;

public interface SupervisorRepo {
    List<Supervisor> getSupervisors();

    void addSupervisor(Supervisor supervisor);
}
