package com.example.TaskManagementApp.server.dto;

import com.example.TaskManagementApp.server.entities.Task;

public class QueryParameterDto {
    private String username;
    private Task.Status status;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public Task.Status getStatus() {
        return status;
    }

    public void setStatus(Task.Status status) {
        this.status = status;
    }

    public static QueryParameterDto create(String username, Task.Status status) {
        QueryParameterDto queryParameters = new QueryParameterDto();
        queryParameters.setUsername(username);
        queryParameters.setStatus(status);
        return queryParameters;
    }
}
