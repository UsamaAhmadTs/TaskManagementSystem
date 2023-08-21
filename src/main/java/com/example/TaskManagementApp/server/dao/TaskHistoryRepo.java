package com.example.TaskManagementApp.server.dao;
import com.example.TaskManagementApp.server.entities.Task;
import com.example.TaskManagementApp.server.entities.TaskHistory;

public interface TaskHistoryRepo {

    TaskHistory getTaskHistory(Task task);

    void setTaskHistory(TaskHistory taskHistory, Task task);
}
