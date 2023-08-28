//package com.example.TaskManagementApp.server.services.implementation;
//
//import com.example.TaskManagementApp.server.dao.SupervisorRepo;
//import com.example.TaskManagementApp.server.entities.Supervisor;
//import com.example.TaskManagementApp.server.services.SupervisorService;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//@Service
//public class SupervisorServiceImpl implements SupervisorService {
//    private final SupervisorRepo supervisorRepo;
//
//    public SupervisorServiceImpl(SupervisorRepo supervisorRepo) {
//        this.supervisorRepo = supervisorRepo;
//    }
//
//    @Override
//    public List<Supervisor> viewSupervisors() {
//        return supervisorRepo.getSupervisors();
//    }
//    public Supervisor verifyCredentials(String username, String password) {
//        return supervisorRepo.findSupervisorByUsernameAndPassword(username, password);
//    }
//}
