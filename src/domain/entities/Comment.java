// Comment.java
package domain.entities;

import java.util.Date;

public class Comment {
    private int commentId;
    private User createdBy;
    private String body;
    private Date createdAt;

    public Comment(int commentId, User createdBy, String body, Date createdAt) {
        this.commentId = commentId;
        this.createdBy = createdBy;
        this.body = body;
        this.createdAt = createdAt;
    }

    public int getCommentId() {
        return commentId;
    }

    public User getCreatedBy() {
        return createdBy;
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
}
