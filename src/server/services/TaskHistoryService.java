package server.services;

import server.entities.TaskHistory;

import java.util.List;

public interface TaskHistoryService {
    List<TaskHistory> viewTaskHistory(String title);

}
