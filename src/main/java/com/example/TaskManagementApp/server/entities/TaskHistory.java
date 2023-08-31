package com.example.TaskManagementApp.server.entities;
import jakarta.persistence.*;
import java.time.Instant;
@Entity
@Table(name = "Task_History")
public class TaskHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_history_id")
    private Long taskHistoryId;

    @Column(name = "TimeStamp",nullable = false)
    private Instant timestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "OldStatus", nullable = false)
    private Task.Status oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "NewStatus", nullable = false)
    private Task.Status newStatus;

    @ManyToOne
    @JoinColumn()
    private User movedBy;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
    public void setTask(Task task) {this.task = task;}
    public Task getTask() {return task;}
    public Instant getTimestamp() {return timestamp;}

    public void setTimestamp(Instant timestamp) {this.timestamp = timestamp;}

    public Task.Status getOldStatus() {return oldStatus;}

    public void setOldStatus(Task.Status oldStatus) {this.oldStatus = oldStatus;}

    public Task.Status getNewStatus() {return newStatus;}

    public void setNewStatus(Task.Status newStatus) {this.newStatus = newStatus;}

    public User getMovedBy() {return movedBy;}
    public void setMovedBy(User movedBy) {this.movedBy = movedBy;}
}

