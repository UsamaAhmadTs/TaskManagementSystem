package com.example.TaskManagementApp.server.entities;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.Entity;

@JsonTypeName("Employee")
@Entity
public class Employee extends User {
    public Employee(String username, String password,UserType userType ) {
        super(username, password, UserType.EMPLOYEE);
    }
    public Employee() {

    }

}
