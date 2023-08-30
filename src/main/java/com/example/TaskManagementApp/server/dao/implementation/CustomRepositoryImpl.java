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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;
    private final UserRepo userRepo;
    public CustomRepositoryImpl(UserRepo userRepo){
        this.userRepo= userRepo;
    }
    public List<Task> getTasks(UserDto authenticatedUser, QueryParameterDto queryParameters) {
        String queryUserName = queryParameters.getUsername();
        Task.Status queryStatus = queryParameters.getStatus();
        User queryUser = userRepo.getUserByUserName(queryUserName);

        String jpql = "SELECT t FROM Task t WHERE 1=1";
        Map<String, Object> parameters = new HashMap<>();

//        StringBuilder queryBuilder = new StringBuilder();
//        queryBuilder.append("SELECT t FROM Task t");

        if (authenticatedUser.getUserType() == User.UserType.EMPLOYEE) {
            jpql += " AND t.assignedEmployee = :employee";
            parameters.put("employee", authenticatedUser);
        } else if (authenticatedUser.getUserType() == User.UserType.MANAGER) {
            jpql += " AND t.createdBy = :user";
            parameters.put("user", authenticatedUser);
        } else {
            // Supervisor can view all tasks
        }

        if (queryStatus != null && !queryStatus.isEmpty()) {
            jpql += " AND t.status = :status";
            parameters.put("status", queryStatus);
        }

        if (queryUser != null && !queryUser.isEmpty()) {
            jpql += " AND t.assignedEmployee.username = :employee";
            parameters.put("employee", queryUser);
        }

        Query query = entityManager.createQuery(jpql);

        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return query.getResultList();
    }
}
