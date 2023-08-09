// UserRepositoryImplementation.java
package server.dao.implementation;
import server.dao.UserRepo;
import server.entities.User;
import java.util.ArrayList;
import java.util.List;

public class UserRepoImplementation implements UserRepo{
    private List<User> users = new ArrayList<>();

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
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void initializeUsers() {
        users.add(new User("user1", "password1", "Employee"));
        users.add(new User("user2", "password2", "Manager"));
        users.add(new User("user2", "password2", "Supervisor"));

    }
}
