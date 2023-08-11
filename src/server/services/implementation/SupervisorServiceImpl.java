package server.services.implementation;

import server.dao.SupervisorRepo;
import server.entities.Supervisor;

import java.util.List;

public class SupervisorServiceImpl implements server.services.SupervisorService {
    private final SupervisorRepo supervisorRepo;

    public SupervisorServiceImpl(SupervisorRepo supervisorRepo) {
        this.supervisorRepo = supervisorRepo;
    }

    @Override
    public List<Supervisor> viewSupervisors() {
        return supervisorRepo.getSupervisors();
    }
    public Supervisor verifyCredentials(String username, String password) {
        return supervisorRepo.findSupervisorByUsernameAndPassword(username, password);
    }
}
