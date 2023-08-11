package server.dao.implementation;
import server.dao.TaskRepo;
import server.entities.Task;
import java.util.ArrayList;
import java.util.List;
public class TaskRepoImplementation implements TaskRepo {
    private List<Task> tasks = new ArrayList<>(List.of(new Task("task", "1", 100)));
    @Override
    public List<Task> getAllTask() {
        return tasks;
    }
    public List<Task> task(){
        return  tasks;
    }
    @Override
    public void addTask(Task task) {
       this.tasks.add(task);
        System.out.println("task added");
    }
}
