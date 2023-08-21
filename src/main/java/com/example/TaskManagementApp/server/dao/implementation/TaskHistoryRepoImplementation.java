package com.example.TaskManagementApp.server.dao.implementation;
import com.example.TaskManagementApp.server.dao.TaskHistoryRepo;
import com.example.TaskManagementApp.server.entities.Task;
import com.example.TaskManagementApp.server.entities.TaskHistory;
import com.example.TaskManagementApp.server.dao.TaskRepo;
import org.springframework.stereotype.Repository;

@Repository
public class TaskHistoryRepoImplementation implements TaskHistoryRepo {


    private final TaskRepo taskRepo;
    private TaskHistory taskHistory;
    public TaskHistoryRepoImplementation(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }
    @Override
    public TaskHistory getTaskHistory(Task task) {

        for(Task giventask:taskRepo.getAllTask())
        {
            if(giventask.getTitle().equals(task.getTitle()))
            {
                return taskHistory;

            }
        }
        return null;
    }
    @Override
    public void setTaskHistory(TaskHistory taskHistory, Task task) {
        for(Task giventask:taskRepo.getAllTask())

        {
            if(giventask.getTitle().equals(task.getTitle()))
            {
                giventask.addTaskHistory(taskHistory);

            }
        }
    }





}
