// UserRepository.java
package server.dao;

import server.entities.User;
import java.util.List;

public interface UserRepo {
    List<User> getUsers();

    void addUser(User user);

    User findUserByUsername(String username);

    void initializeUsers();
}
