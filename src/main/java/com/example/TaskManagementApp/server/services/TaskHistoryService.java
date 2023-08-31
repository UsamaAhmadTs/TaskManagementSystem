package com.example.TaskManagementApp.server.services;
import com.example.TaskManagementApp.server.dto.UserDto;
import com.example.TaskManagementApp.server.entities.TaskHistory;
import java.util.List;
public interface TaskHistoryService {
   List<TaskHistory> getTaskHistory(UserDto authenticatedUser, String title);

}
