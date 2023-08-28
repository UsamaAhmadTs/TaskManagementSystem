package com.example.TaskManagementApp.server.entities;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.Entity;

@JsonTypeName("Manager")
@Entity
public class Manager extends User {
    public Manager(String userName, String password) {
        super(userName, password, UserType.MANAGER);
    }

}