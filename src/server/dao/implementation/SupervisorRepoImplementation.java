package server.dao.implementation;

import server.dao.SupervisorRepo;
import server.entities.Manager;
import server.entities.Supervisor;
import java.util.ArrayList;
import java.util.List;

public class SupervisorRepoImplementation implements SupervisorRepo {
    private List<Supervisor> supervisors;

    public SupervisorRepoImplementation() {
        supervisors = new ArrayList<>(List.of(new Supervisor("usama", "1")));

    }
    public Supervisor findSupervisorByUsernameAndPassword(String username, String password) {
        for (Supervisor supervisor : supervisors) {
            if (supervisor.getUsername().equals(username) && supervisor.getPassword().equals(password)) {
                return supervisor;
            }
        }
        return null;
    }
    @Override
    public List<Supervisor> getSupervisors() {
        return supervisors;
    }

    @Override
    public void addSupervisor(Supervisor supervisor) {
        supervisors.add(supervisor);
    }
}
