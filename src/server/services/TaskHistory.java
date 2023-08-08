package server.services;

import java.util.List;

public interface TaskHistory {
    List<server.entities.TaskHistory> viewTaskHistory(String title);

}
