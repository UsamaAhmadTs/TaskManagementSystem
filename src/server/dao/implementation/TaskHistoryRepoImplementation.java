package server.dao.implementation;
import server.dao.TaskHistoryRepo;
import server.dao.TaskRepo;
import server.entities.Task;
import server.entities.TaskHistory;


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
                giventask.setTaskHistory(taskHistory);

            }
        }
    }





}
