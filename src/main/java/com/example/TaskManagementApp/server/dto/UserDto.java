package com.example.TaskManagementApp.server.dto;

import com.example.TaskManagementApp.server.entities.User;
import org.springframework.stereotype.Component;

public class UserDto {
    private String userName;
    private User.UserType userType;
    private String password;


    public UserDto(String userName, String password, User.UserType userType) {
        this.userName = userName;
        this.userType = userType;
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User.UserType getUserType() {
        return userType;
    }

    public void setUserType(User.UserType userType) {
        this.userType = userType;
    }

    // Factory methods for converting between Entity and DTO
    public static UserDto fromEntity(User user) {
        return new UserDto( user.getUserName(), user.getPassword(),user.getUserType());
    }

    public User toEntity() {
        return new User(userName, password, userType);
    }
    @Override
    public String toString() {
        return "UserDto{" +
                "userName='" + userName + '\'' +
                ", userType=" + userType +
                ", password='" + password + '\'' +
                '}';
    }
}
