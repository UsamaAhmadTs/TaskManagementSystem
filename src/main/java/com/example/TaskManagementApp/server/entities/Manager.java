package com.example.TaskManagementApp.server.entities;

public class Manager extends User {
    public Manager(String userName, String password) {
        super(userName, password, UserType.MANAGER);
    }

}