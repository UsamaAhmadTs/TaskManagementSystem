
package domain.Dao;
import domain.entities.Manager;
import domain.entities.User;
import java.util.List;


public interface ManagerRepo {
    List<Manager> getManagers();

    void addManager(Manager manager);

    Manager createManager(String username, String password);

    List<User> getManagersByName(String name);

    Manager findManager(String username);
}
