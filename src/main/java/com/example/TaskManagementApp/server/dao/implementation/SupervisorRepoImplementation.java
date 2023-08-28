//package com.example.TaskManagementApp.server.dao.implementation;
//
//import com.example.TaskManagementApp.server.dao.SupervisorRepo;
//import com.example.TaskManagementApp.server.entities.Supervisor;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//@Repository
//public class SupervisorRepoImplementation implements SupervisorRepo {
//    private List<Supervisor> supervisors;
//
//    public SupervisorRepoImplementation() {
//        supervisors = new ArrayList<>(List.of(new Supervisor("usama", "1")));
//
//    }
//    public Supervisor findSupervisorByUsernameAndPassword(String username, String password) {
//        for (Supervisor supervisor : supervisors) {
//            if (supervisor.getUserName().equals(username) && supervisor.getPassword().equals(password)) {
//                return supervisor;
//            }
//        }
//        return null;
//    }
//    @Override
//    public List<Supervisor> getSupervisors() {
//        return supervisors;
//    }
//
//    @Override
//    public void addSupervisor(Supervisor supervisor) {
//        supervisors.add(supervisor);
//    }
//}
