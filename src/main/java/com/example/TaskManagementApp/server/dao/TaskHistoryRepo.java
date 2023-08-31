package com.example.TaskManagementApp.server.dao;
import com.example.TaskManagementApp.server.entities.Task;
import com.example.TaskManagementApp.server.entities.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskHistoryRepo extends JpaRepository<TaskHistory, String> {
    List<TaskHistory> getTaskHistoriesByTask(Task task);
}
