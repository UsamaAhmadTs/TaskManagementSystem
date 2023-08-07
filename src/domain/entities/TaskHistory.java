// TaskHistory.java
package domain.entities;

import java.util.Date;

public class TaskHistory {
    private int taskId;
    private Date timestamp;
    private String oldStatus;
    private String newStatus;
    private User movedBy;

    public TaskHistory(int taskId, Date timestamp, String oldStatus, String newStatus, User movedBy) {
        this.taskId = taskId;
        this.timestamp = timestamp;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.movedBy = movedBy;
    }

    public int getTaskId() {
        return taskId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public User getMovedBy() {
        return movedBy;
    }
}
