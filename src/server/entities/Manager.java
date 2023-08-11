package server.entities;

public class Manager extends User {
    public Manager(String username, String password) {
        super(username, password, "Manager");
    }

    public Manager() {

    }
}