// UserRepositoryImplementation.java
package com.example.TaskManagementApp.server.dao.implementation;

import com.example.TaskManagementApp.server.entities.Employee;
import com.example.TaskManagementApp.server.entities.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import com.example.TaskManagementApp.server.dao.UserRepo;

import java.util.ArrayList;
import java.util.List;
@Repository

public class UserRepoImplementation implements UserRepo{
    private List<User> users = new ArrayList<>(List.of(new Employee("usama", "1")));

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
        public User findUserByUsername(String username) {
            for (User user : users) {
                System.out.println("Checking user: " + user.getUserName());
                if (user.getUserName().equals(username)) {
                    System.out.println("User found: " + user.getUserName());
                    return user;
                }
            }
            System.out.println("User not found");
            return null;
        }


    @Override
    public void initializeUsers() {
        users.add(new User("user2", "password2", User.UserType.MANAGER));
        users.add(new User("user3", "password3", User.UserType.SUPERVISOR));

    }
}
