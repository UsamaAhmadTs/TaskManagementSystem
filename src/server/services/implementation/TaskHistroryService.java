//package server.services.implementation;
//        import server.dao.TaskHistoryRepo;
//        import server.entities.TaskHistory;
//        import server.services.TaskHistoryService;
//        import java.util.List;
//
//
//public class TaskHistoryService implements TaskHistoryService {
//    private final TaskHistoryRepo taskHistoryRepo;
//
//    public TaskHistoryService(TaskHistoryRepo taskHistoryRepo) {
//        this.taskHistoryRepo = taskHistoryRepo;
//    }
//
//    @Override
//    public List<TaskHistory> viewTaskHistory(String title) {
//        return taskHistoryRepo.getTaskHistoryByTitle(title);
//    }
//}
