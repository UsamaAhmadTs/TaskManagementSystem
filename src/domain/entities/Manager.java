package domain.entities;

public class Manager extends User {
    public Manager(int userId, String username) {
        super(userId, username, "Manager");
    }
}