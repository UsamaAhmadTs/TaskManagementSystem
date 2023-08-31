package com.example.TaskManagementApp.server.dao.implementation;

import com.example.TaskManagementApp.server.dao.UserRepo;
import com.example.TaskManagementApp.server.dao.customRepository;
import com.example.TaskManagementApp.server.dto.QueryParameterDto;
import com.example.TaskManagementApp.server.dto.UserDto;
import com.example.TaskManagementApp.server.entities.Task;
import com.example.TaskManagementApp.server.entities.User;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.*;

@Repository
public class customRepositoryImpl implements customRepository {

    @PersistenceContext
    private EntityManager entityManager;
    private final UserRepo userRepo;

    public customRepositoryImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<Task> getTasks(UserDto authenticatedUser, QueryParameterDto queryParameters) {
        String queryUserName = queryParameters.getUsername();
        Task.Status queryStatus = queryParameters.getStatus();
        User queryUser = userRepo.getUserByUserName(queryUserName);
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT t FROM Task t WHERE 1=1");

        Map<String, Object> parameters = new HashMap<>();

        if (authenticatedUser.getUserType() == User.UserType.EMPLOYEE) {
            addEmployeeCriteria(authenticatedUser, queryStatus, jpql, parameters);
        } else if (authenticatedUser.getUserType() == User.UserType.MANAGER) {
            addManagerCriteria(authenticatedUser, queryUser, queryStatus, jpql, parameters);
        } else if (authenticatedUser.getUserType() == User.UserType.SUPERVISOR) {
            addSupervisorCriteria(queryUser, queryStatus, jpql, parameters);
        }

        TypedQuery<Task> query = entityManager.createQuery(jpql.toString(), Task.class);

        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return query.getResultList();
    }

    private void addEmployeeCriteria(UserDto authenticatedUser, Task.Status queryStatus,
                                     StringBuilder jpql, Map<String, Object> parameters) {
        jpql.append(" AND t.assignee.userName = :userName");
        parameters.put("userName", authenticatedUser.getUserName());

        if (queryStatus != null) {
            jpql.append(" AND t.taskStatus = :status");
            parameters.put("status", queryStatus);
        }
    }

    private void addManagerCriteria(UserDto authenticatedUser, User queryUser,
                                    Task.Status queryStatus, StringBuilder jpql, Map<String, Object> parameters) {
        if (queryUser != null && !queryUser.getUserName().isEmpty() && queryUser.getUserType().equals(User.UserType.MANAGER)) {
            jpql.append(" AND t.createdBy.userName = :authUserName");
            parameters.put("authUserName", authenticatedUser.getUserName());
        }
        if (queryUser != null && !queryUser.getUserName().isEmpty()&& queryUser.getUserType().equals(User.UserType.EMPLOYEE)) {
            jpql.append(" AND t.assignee.userName = :queryUserName");
            parameters.put("queryUserName", queryUser.getUserName());
        }

        if (queryStatus != null) {
            jpql.append(" AND t.taskStatus = :status");
            parameters.put("status", queryStatus);
        }
    }

    private void addSupervisorCriteria(User queryUser, Task.Status queryStatus,
                                       StringBuilder jpql, Map<String, Object> parameters) {
        if (queryStatus != null) {
            jpql.append(" AND t.taskStatus = :status");
            parameters.put("status", queryStatus);
        }

        if (queryUser != null && !queryUser.getUserName().isEmpty() && queryStatus != null && queryUser.getUserType().equals(User.UserType.EMPLOYEE)) {
            jpql.append(" AND t.assignee.userName = :userName AND t.taskStatus = :status");
            parameters.put("userName", queryUser.getUserName());
            parameters.put("status", queryStatus);
        }
        if (queryUser != null && !queryUser.getUserName().isEmpty() && queryUser.getUserType().equals(User.UserType.MANAGER)) {
            jpql.append(" AND t.createdBy.userName = :authUserName");
            parameters.put("authUserName", queryUser.getUserName());
        }
    }
}



