package com.example.TaskManagementApp.server.entities;

public class Employee extends User {
    public Employee(String username, String password) {
        super(username, password, UserType.EMPLOYEE);
    }
}
