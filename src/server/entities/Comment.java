// Comment.java
package server.entities;

import java.util.Date;

public class Comment {
    private User createdBy;
    private String body;

    //replace date with Instant
    private Date createdAt;

    private Task tasks;
    public User getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public void addTaskForComment(Task task) {
        this.tasks = task;
    }
}
