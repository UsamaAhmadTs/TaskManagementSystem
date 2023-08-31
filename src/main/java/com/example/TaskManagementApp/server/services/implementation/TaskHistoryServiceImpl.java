package com.example.TaskManagementApp.server.services.implementation;
import com.example.TaskManagementApp.server.dao.TaskHistoryRepo;
import com.example.TaskManagementApp.server.dao.TaskRepo;
import com.example.TaskManagementApp.server.dto.UserDto;
import com.example.TaskManagementApp.server.entities.Task;
import com.example.TaskManagementApp.server.entities.TaskHistory;
import com.example.TaskManagementApp.server.entities.User;
import com.example.TaskManagementApp.server.services.TaskHistoryService;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TaskHistoryServiceImpl implements TaskHistoryService {
    private final TaskRepo taskRepo;
    private final TaskHistoryRepo taskHistoryRepo;
    public TaskHistoryServiceImpl(TaskRepo taskRepo, TaskHistoryRepo taskHistoryRepo) {
        this.taskRepo = taskRepo;
        this.taskHistoryRepo = taskHistoryRepo;
    }
    @Override
    public List<TaskHistory> getTaskHistory(UserDto authenticatedUser, String title) {
        Task task = taskRepo.getTaskByTaskTitle(title);

        if (!Objects.isNull(task) && authenticatedUser.getUserType().equals(User.UserType.SUPERVISOR)) {
            List<TaskHistory> taskHistories = taskHistoryRepo.getTaskHistoriesByTask(task);

            for (TaskHistory history : task.getTaskHistory(task)) {
                Instant timestamp = history.getTimestamp();
                LocalDateTime localDateTime = timestamp.atZone(ZoneId.systemDefault()).toLocalDateTime();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = localDateTime.format(formatter);

                Task.Status oldStatus = history.getOldStatus();
                Task.Status newStatus = history.getNewStatus();
                User movedBy = history.getMovedBy();

                TaskHistory taskHistory = new TaskHistory();
                taskHistory.setMovedBy(movedBy);
                taskHistory.setOldStatus(oldStatus);
                taskHistory.setNewStatus(newStatus);
                taskHistory.setTimestamp(Instant.parse(formattedDateTime));
                taskHistories.add(taskHistory);
            }

            return taskHistories;
        } else {
            return new ArrayList<>();
        }
    }
}
