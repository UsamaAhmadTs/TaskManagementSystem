// Task.java
package com.example.TaskManagementApp.server.entities;

import com.example.TaskManagementApp.server.dto.TaskDto;

import java.time.Instant;
import java.util.List;

public class Task {
//    public static Task.Status Status;
    private Status taskStatus;
    private String title;
    private String description;
    private User createdBy;
    private Instant createdAt;
    private Manager assignee;
    private int totalTime;
    private boolean assigned;
    private Employee assignedTo;
    private List<TaskHistory> taskHistoryList;

    public Task() {
        this.taskStatus = null;
        this.title = null;
        this.description = null;
        this.createdAt = null;
        this.createdBy = null;
        this.totalTime = 0;
        this.assignee = null;
    }

    public enum Status {
        CREATED,
        IN_PROGRESS,
        COMPLETED,
        IN_REVIEW
    }
    public Task(String title, String description,  int totalTime, Instant createdAt, User createdBy ) {
        this.setTitle(title);
        this.setAssigned(false);
        this.setDescription(description);
        this.setTotalTime(totalTime);
        this.setTaskStatus(Status.CREATED);
        this.setCreatedAt(createdAt);
        this.setCreatedBy(createdBy);
    }


    public Employee getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Employee assignedTo) {
        this.assignedTo = assignedTo;
    }
    public Status getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Status taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public Manager getAssignee() {
        return assignee;
    }

    public void setAssignee(Manager assignee) {
        this.assignee = assignee;
    }

    public int getTotalTime() {

        return totalTime;
    }

    public void setTotalTime(int totalTime) {

        this.totalTime = totalTime;
    }
    public void addTaskHistory(TaskHistory taskHistory) {
        taskHistoryList.add(taskHistory);
    }

    public List<TaskHistory> getTaskHistory() {
        return taskHistoryList;
    }




    public static Task fromDto(TaskDto task) {
        return new Task( task.getTitle(), task.getDescription(),task.getTotalTime(),task.getCreatedAt(), task.getCreatedBy());
    }

}
