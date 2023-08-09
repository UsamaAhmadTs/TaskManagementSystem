package server.dao;

import server.entities.Task;

import java.util.List;

public interface TaskRepo {

    List<Task> getAllTask();
    List<Task> task();

    void addTask(Task allTasks);


}