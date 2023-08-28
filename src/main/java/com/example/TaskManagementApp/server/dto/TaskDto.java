package com.example.TaskManagementApp.server.dto;

import com.example.TaskManagementApp.server.entities.Task;
import com.example.TaskManagementApp.server.entities.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Instant;

public class TaskDto {
    private Task.Status status;
    private String title;
    private String description;
    private String createdBy;
    private Instant createdAt;
    private int totalTime;
    private boolean assigned;


//    public enum Status {
//        CREATED,
//        IN_PROGRESS,
//        COMPLETED,
//        IN_REVIEW
//    }
    public TaskDto(String title, String description, int totalTime,Instant createdAt, String createdBy) {
        this.setStatus(Task.Status.CREATED);
        this.title = title;
        this.description = description;
        this.totalTime = totalTime;
        this.setAssigned(false);
        this.createdAt= createdAt;
        this.createdBy= createdBy;
    }

    public Task.Status getStatus() {
        return status;
    }

    public void setStatus(Task.Status status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }
//    public static TaskDto fromEntity(Task task) {
//        return new TaskDto( task.getTitle(), task.getDescription(),task.getTotalTime(),task.getCreatedAt(), task.getCreatedBy());
//    }
}
