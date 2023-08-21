package com.example.TaskManagementApp.server.entities;

public class Supervisor extends User {
    public Supervisor(String username, String password) {
        super(username, password, UserType.SUPERVISOR);
    }
}
