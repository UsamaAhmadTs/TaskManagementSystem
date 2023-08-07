// Task.java
package domain.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Task {
    private int taskId;
    private String title;
    private String description;
    private User createdBy;
    private Date createdAt;
    private User assignee;
    private int totalTime;
    private List<Comment> comments;
    private String status;
    private List<TaskHistory> taskHistory;
    public Task(int taskId, String title, String description, User createdBy, Date createdAt) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.comments = new ArrayList<>();
        this.taskHistory = new ArrayList<>();
        this.status = "CREATED";
    }

    public int getTaskId() {
        return taskId;
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

    public Date getCreatedAt() {
        return createdAt;
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

    public List<Comment> getComments() {
        return comments;
    }

    public String getStatus() {
        return status;
    }

//    public void setStatus(String status) {
//        this.status = status;
//    }

    public void setStatus(String newStatus, User movedBy) {
        TaskHistory history = new TaskHistory(this.taskId, new Date(), this.status, newStatus, movedBy);
        taskHistory.add(history);  // Record status transition in history
        this.status = newStatus;
    }

    public List<TaskHistory> getTaskHistory() {
        return taskHistory;
    }
}
