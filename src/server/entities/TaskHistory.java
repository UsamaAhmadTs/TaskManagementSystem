// TaskHistory.java
package server.entities;

import java.time.Instant;
import java.util.Date;

public class TaskHistory {
    private Instant timestamp;
    private String oldStatus;
    private String newStatus;
    private String movedBy;

    public TaskHistory(Instant timestamp, String oldStatus, String newStatus, String movedBy) {
        this.timestamp = timestamp;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.movedBy = movedBy;
    }


    public Instant getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Instant timestamp) {
            this.timestamp = timestamp;
        }

    public String getOldStatus() {
        return oldStatus;
    }
    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }
    public String getNewStatus() {
        return newStatus;
    }
    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }
    public String getMovedBy() {
        return movedBy;
    }
    public void setMovedBy(String movedBy) {
        this.movedBy = movedBy;
    }

}
