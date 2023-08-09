package server.services.implementation;

import server.entities.Task;
import server.entities.TaskHistory;
import server.services.TaskHistoryInterface;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaskHistoryService implements TaskHistoryInterface {

    private final TaskService taskService;

    public TaskHistoryService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public List<TaskHistory> viewTaskHistory(String title) {
        Task task = taskService.getTaskByTitle(title);
        if (task != null) {
            List<TaskHistory> taskHistories = new ArrayList<>();
            TaskHistory history = task.getTaskHistory();

            Instant timestamp = history.getTimestamp();
            LocalDateTime localDateTime = timestamp.atZone(ZoneId.systemDefault()).toLocalDateTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = localDateTime.format(formatter);

            String oldStatus = history.getOldStatus().toString();
            String newStatus = history.getNewStatus().toString();
            String movedBy = history.getMovedBy();

            TaskHistory taskHistory = new TaskHistory(timestamp, oldStatus, newStatus, movedBy);
            taskHistory.setMovedBy(movedBy);
            taskHistory.setOldStatus(oldStatus);
            taskHistory.setNewStatus(newStatus);

            taskHistories.add(taskHistory);
            return taskHistories;
        } else {
            return null;
        }
    }
}
