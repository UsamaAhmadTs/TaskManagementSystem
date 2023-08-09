package server.dao;
import server.entities.Task;
import server.entities.TaskHistory;

public interface TaskHistoryRepo {

    TaskHistory getTaskHistory(Task task);

    void setTaskHistory(TaskHistory taskHistory, Task task);
}
