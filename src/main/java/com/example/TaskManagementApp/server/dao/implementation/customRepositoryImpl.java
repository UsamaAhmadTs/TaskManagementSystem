package com.example.TaskManagementApp.server.dao.implementation;

import com.example.TaskManagementApp.server.dao.UserRepo;
import com.example.TaskManagementApp.server.dto.QueryParameterDto;
import com.example.TaskManagementApp.server.dto.UserDto;
import com.example.TaskManagementApp.server.entities.Task;
import com.example.TaskManagementApp.server.entities.User;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.*;

@Repository
public class customRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;
    private final UserRepo userRepo;
    public customRepositoryImpl(UserRepo userRepo){
        this.userRepo= userRepo;
    }
    public List<Task> getTasks(UserDto authenticatedUser, QueryParameterDto queryParameters) {
        String queryUserName = queryParameters.getUsername();
        Task.Status queryStatus = queryParameters.getStatus();
        String jpql = "SELECT t FROM Task t WHERE 1=1";
        Map<String, Object> parameters = new HashMap<>();

        if (authenticatedUser.getUserType() == User.UserType.EMPLOYEE) {
            addEmployeeCriteria(authenticatedUser, queryStatus, jpql, parameters);
        } else if (authenticatedUser.getUserType() == User.UserType.MANAGER) {
            addManagerCriteria(authenticatedUser, queryUserName, queryStatus, jpql, parameters);
        } else if (authenticatedUser.getUserType() == User.UserType.SUPERVISOR) {
            addSupervisorCriteria(queryUserName, queryStatus, jpql, parameters);
        }

        Query query = entityManager.createQuery(jpql, Task.class);

        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return query.getResultList();
    }

    private void addEmployeeCriteria(UserDto authenticatedUser, Task.Status queryStatus,
                                     String jpql, Map<String, Object> parameters) {
        jpql += " AND t.assignee = :employee";
        parameters.put("employee", authenticatedUser);

        if (queryStatus != null) {
            jpql += " AND t.task_status = :status";
            parameters.put("status", queryStatus);
        }
    }

    private void addManagerCriteria(UserDto authenticatedUser, String queryUserName,
                                    Task.Status queryStatus, String jpql, Map<String, Object> parameters) {
        jpql += " AND t.created_by = :user";
        parameters.put("user", authenticatedUser);

        // Manager can view all tasks by employee he assigned
        if (queryUserName != null && !queryUserName.isEmpty()) {
            jpql += " AND t.assignee.username = :employee";
            parameters.put("employee", queryUserName);
        }

        if (queryStatus != null) {
            jpql += " AND t.task_status = :status";
            parameters.put("status", queryStatus);
        }
    }

    private void addSupervisorCriteria(String queryUserName, Task.Status queryStatus,
                                       String jpql, Map<String, Object> parameters) {
        if (queryStatus != null) {
            jpql += " AND t.task_status = :status";
            parameters.put("status", queryStatus);
        }
        // Example: View tasks by employee with status
        if (queryUserName != null && !queryUserName.isEmpty() && queryStatus != null) {
            jpql += " AND t.assignee.username = :user AND t.task_status = :status";
            parameters.put("user", queryUserName);
            parameters.put("status", queryStatus);
        }

        // Example: View tasks by Manager with Status (assuming manager field is present in the entity)
        // if (queryUserName != null && !queryUserName.isEmpty() && queryStatus != null) {
        //     jpql += " AND t.manager.username = :manager AND t.taskStatus = :status";
        //     parameters.put("manager", queryUserName);
        //     parameters.put("status", queryStatus);
        // }
    }

}
