package com.example.TaskManagementApp.server.entities;
import com.example.TaskManagementApp.server.dto.TaskDto;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private Status taskStatus;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(nullable = false)
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "assignee-name")
    private User assignee;
    @Column(name = "totalTime",nullable = false,length = 10)
    private int totalTime;
    @Column(name = "isAssigned",nullable = false)
    private boolean assigned;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id")
    private List<TaskHistory> taskHistory = new ArrayList<>();
    public Task() {

    }
    public Task(String title, String description, int totalTime) {
        this.setTaskStatus(Status.CREATED);
        this.setTitle(title);
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
    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
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

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public int getTotalTime() {

        return totalTime;
    }

    public void setTotalTime(int totalTime) {

        this.totalTime = totalTime;
    }
    public List<TaskHistory> getHistory(Task task) {
        return task.taskHistory;
    }

    public void setHistory(TaskHistory history) {
        taskHistory.add(history);
        history.setTask(this);
    }




//    public static Task fromDto(TaskDto task) {
//        return new Task( task.getTitle(), task.getDescription(),task.getTotalTime(),task.getCreatedAt(), task.getCreatedBy());
//    }

}
