// Task.java
package server.entities;

import java.util.ArrayList;
import java.util.List;

public class Task {

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
    private Status taskStatus;
    private String title;
    private String description;
    private String createdBy;
    private String createdAt;
    private String assignee;
    private int totalTime;
    private List<Comment> comments;
    private boolean assigned;
    private String status;
    private TaskHistory taskHistory;
    public Task(String title, String description,  int totalTime ) {
        this.setTitle(title);
        this.setDescription(description);
        this.setTotalTime(totalTime);
        this.comments = new ArrayList<>();
        this.status = "CREATED";
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

    public String getCreatedAt() {

        return createdAt;
    }
    public void setCreatedAt(String createdAt) {

        this.createdAt = createdAt;
    }
    public void setCreatedBy(String createdBy) {

        this.createdBy = createdBy;

    }
    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public String getAssignee() {

        return assignee;
    }

    public void setAssignee(String assignee) {

        this.assignee = assignee;
    }

    public int getTotalTime() {

        return totalTime;
    }

    public void setTotalTime(int totalTime) {

        this.totalTime = totalTime;
    }
    public TaskHistory getTaskHistory() {

        return taskHistory;
    }

    public void setTaskHistory(TaskHistory taskHistory) {

        this.taskHistory = taskHistory;
    }

    public List<Comment> getComments() {

        return comments;
    }

    public String getStatus() {

        return status;
    }
    public void setStatus(Status taskStatus) {
        this.taskStatus = taskStatus;
    }

}
