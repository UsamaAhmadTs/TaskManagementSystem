package com.example.TaskManagementApp.server.entities;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.Entity;

@JsonTypeName("Supervisor")
@Entity
public class Supervisor extends User {
    public Supervisor(String username, String password) {
        super(username, password, UserType.SUPERVISOR);
    }
}
