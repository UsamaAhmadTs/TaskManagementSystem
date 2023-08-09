package server.dao.implementation;

import server.dao.SupervisorRepo;
import server.entities.Supervisor;
import java.util.ArrayList;
import java.util.List;

public class SupervisorRepoImplementation implements SupervisorRepo {
    private List<Supervisor> supervisors;

    public SupervisorRepoImplementation() {
        supervisors = new ArrayList<>();
        // You can initialize supervisors here if needed
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
