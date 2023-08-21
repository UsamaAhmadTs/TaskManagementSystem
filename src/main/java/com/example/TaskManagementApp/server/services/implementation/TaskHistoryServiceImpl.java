package com.example.TaskManagementApp.server.services.implementation;

//import server.config.ServerConfig;
import com.example.TaskManagementApp.server.entities.TaskHistory;
import com.example.TaskManagementApp.server.services.TaskService;
import com.example.TaskManagementApp.server.services.TaskHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskHistoryServiceImpl implements TaskHistoryService {
    //private static TaskService taskService = ServerConfig.getTaskService();

    public TaskHistoryServiceImpl(TaskService taskService) {

        //this.taskService = taskService;
    }

    @Override
    public List<TaskHistory> viewTaskHistory(String title) {
//        Task task = taskService.getTaskByTitle(title);
//        if (task != null) {
//            List<TaskHistory> taskHistories = new ArrayList<>();
//            TaskHistory history = (TaskHistory) task.getTaskHistory();
//            //List<TaskHistory> history = task.getTaskHistory();
//
//            Instant timestamp = history.getTimestamp();
//            LocalDateTime localDateTime = timestamp.atZone(ZoneId.systemDefault()).toLocalDateTime();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            String formattedDateTime = localDateTime.format(formatter);
//
//            Task.Status oldStatus = history.getOldStatus();
//            Task.Status newStatus = history.getNewStatus();
//
//            User movedBy = history.getMovedBy();
//
//            TaskHistory taskHistory = new TaskHistory(timestamp, oldStatus, newStatus, movedBy);
//            taskHistory.setMovedBy(movedBy);
//            taskHistory.setOldStatus(oldStatus);
//            taskHistory.setNewStatus(newStatus);
//
//            taskHistories.add(taskHistory);
//            return taskHistories;
//        } else {
//            return null;
//        }
        return null;
    }
}
