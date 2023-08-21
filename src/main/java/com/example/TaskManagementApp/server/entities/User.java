package com.example.TaskManagementApp.server.entities;

public class User {
    private String userName;
    private String password;
    private UserType  userType;

    public enum UserType {
        EMPLOYEE,
        MANAGER,
        SUPERVISOR
    }


    public User(String userName, String password, UserType userType) {
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userType=" + userType +
                '}';
    }
}
