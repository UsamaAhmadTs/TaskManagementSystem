// Task.java
package domain.entities;

import java.util.ArrayList;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class Task {
    private String title;
    private String description;
    private User createdBy;
    private Date createdAt;
    private User assignee;
    private int totalTime;
    private List<Comment> comments;
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

    public User getCreatedBy() {

        return createdBy;
    }

    public Date getCreatedAt() {

        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
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
    public String setStatus() {

        return status;
    }

}
