
package domain.services;
import domain.entities.User;
import java.util.List;

public interface UserInterface {
    User createUser(String username, String password);

    List<User> getAllUsers();

    boolean verifyUser(String username, String password);
}
