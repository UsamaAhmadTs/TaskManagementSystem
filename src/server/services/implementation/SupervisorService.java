package server.services.implementation;

import server.dao.SupervisorRepo;
import server.entities.Supervisor;
import server.services.SupervisorInterface;

import java.util.List;

public class SupervisorService implements SupervisorInterface {
    private final SupervisorRepo supervisorRepo;

    public SupervisorService(SupervisorRepo supervisorRepo) {
        this.supervisorRepo = supervisorRepo;
    }

    @Override
    public List<Supervisor> viewSupervisors() {
        return supervisorRepo.getSupervisors();
    }
}
