package com.example.TaskManagementApp.server.entities;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table()
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Status taskStatus;

    @Column(name = "taskTitle",nullable = false)
    private String taskTitle;

    @Column(name = "description",length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "createdBy")
    private User createdBy;

    @Column(name = "createdAt",nullable = false)
    private Instant createdAt;

    @Column(name = "totalTime",nullable = false,length = 10)
    private int totalTime;
    @Column(nullable = false)
    private boolean assigned;

    @ManyToOne
    @JoinColumn(name = "assignee")
    private User assignee;
    @Column(name = "start_Time")
    private Instant startTime;

    public Task() {}
    public Task(String taskTitle, String description, int totalTime) {
        this.setTaskStatus(Status.CREATED);
        this.setTaskTitle(taskTitle);
        this.setDescription(description);
        this.setTotalTime(totalTime);
        this.setAssigned(false);
    }



    public enum Status {
        CREATED,
        IN_PROGRESS,
        COMPLETED,
        IN_REVIEW
    }
    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }
    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignedTo) {
        this.assignee = assignedTo;
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

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String title) {
        this.taskTitle = title;
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

    public int getTotalTime() {

        return totalTime;
    }

    public void setTotalTime(int totalTime) {

        this.totalTime = totalTime;
    }
//    public List<TaskHistory> getTaskHistory(Task task) {
//        return task.taskHistory;
//    }
//
//    public void setTaskHistory(TaskHistory history) {
//        taskHistory.add(history);
//        history.setTask(this);
//    }




//    public static Task fromDto(TaskDto task) {
//        return new Task( task.getTitle(), task.getDescription(),task.getTotalTime(),task.getCreatedAt(), task.getCreatedBy());
//    }

}
