package server.services;

import server.entities.TaskHistory;

import java.util.List;

public interface TaskHistoryInterface {
    List<TaskHistory> viewTaskHistory(String title);

}
