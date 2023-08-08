// TaskHistory.java
package domain.entities;

import java.util.Date;

public class TaskHistory {
    private Date timestamp;
    private String oldStatus;
    private String newStatus;
    private User movedBy;

    public TaskHistory(Date timestamp, String oldStatus, String newStatus, User movedBy) {
        this.timestamp = timestamp;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.movedBy = movedBy;
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
