package server.dao;

import server.entities.Task;

import java.util.List;

public interface TaskRepo {

    List<Task> getAllTask();

    void addTask(Task allTasks);


}