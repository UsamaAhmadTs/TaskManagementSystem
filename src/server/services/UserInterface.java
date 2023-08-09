
package server.services;
import server.entities.User;
import java.util.List;

public interface UserInterface {
    String createUser(String username, String password, String userType);

    List<User> getAllUsers();
}
