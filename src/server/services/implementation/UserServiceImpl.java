
package server.services.implementation;
import server.dao.EmployeeRepo;
import server.dao.ManagerRepo;
import server.dao.UserRepo;
import server.entities.Employee;
import server.entities.Manager;
import server.entities.User;

import java.util.List;


public class UserServiceImpl implements server.services.UserService {
    private final UserRepo userRepository;
    private final ManagerRepo ManagerRepo;
    private final EmployeeRepo EmployeeRepo;

    public UserServiceImpl(UserRepo userRepository, ManagerRepo ManagerRepo, EmployeeRepo EmployeeRepo) {
        this.ManagerRepo = ManagerRepo;
        this.userRepository = userRepository;
        this.EmployeeRepo = EmployeeRepo;
        this.userRepository.initializeUsers(); // Initialize users
    }
    @Override
    public String createUser(String username, String password, String userType) {
        if (userType.equals("Employee")) {
            Employee employee = EmployeeRepo.createEmployee(username, password);
            userRepository.addUser(employee);
            return "success";
        } else if (userType.equals("Manager")) {

            Manager manager= ManagerRepo.createManager(username, password);
            userRepository.addUser(manager);
            return "success";
        }
        else {
            return "error";
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getUsers();
    }

}

