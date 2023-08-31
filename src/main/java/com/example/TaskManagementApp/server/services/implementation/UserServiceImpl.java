
package com.example.TaskManagementApp.server.services.implementation;
import com.example.TaskManagementApp.server.dao.EmployeeRepo;
import com.example.TaskManagementApp.server.dao.ManagerRepo;
import com.example.TaskManagementApp.server.dao.UserRepo;
import com.example.TaskManagementApp.server.dto.UserDto;
import com.example.TaskManagementApp.server.dto.UsernamePasswordDto;
import com.example.TaskManagementApp.server.entities.Employee;
import com.example.TaskManagementApp.server.entities.Manager;
import com.example.TaskManagementApp.server.entities.Supervisor;
import com.example.TaskManagementApp.server.entities.User;
import com.example.TaskManagementApp.server.exception.ForbiddenAccessException;
import com.example.TaskManagementApp.server.exception.UnauthorizedAccessException;
import com.example.TaskManagementApp.server.exception.UserNotFoundException;
import com.example.TaskManagementApp.server.services.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final ManagerRepo managerRepo;
    private final EmployeeRepo employeeRepo;
    private EntityManagerFactory emf;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepo userRepository, EntityManagerFactory emf, ManagerRepo managerRepo, EmployeeRepo employeeRepo) {
        this.managerRepo = managerRepo;
        this.userRepo = userRepository;
        this.employeeRepo = employeeRepo;
        this.emf = emf;
    }
    public void initializeUsers() {
            Employee employee = new Employee();
            employee.setUserName("e1");
            employee.setPassword("1");
            employee.setUserType(User.UserType.EMPLOYEE);
            Manager manager = new Manager();
            manager.setUserName("m1");
            manager.setPassword("1");
            manager.setUserType(User.UserType.MANAGER);
            Supervisor supervisor = new Supervisor();
            supervisor.setUserName("s1");
            supervisor.setPassword("1");
            supervisor.setUserType(User.UserType.SUPERVISOR);
            userRepo.save(employee);
            userRepo.save(manager);
            userRepo.save(supervisor);
        }
@Override
public void createUser(UserDto userDto, UserDto authenticatedUser) {
    validateUser(authenticatedUser);
    if (userDto.getUserType() == User.UserType.EMPLOYEE) {
        Employee employee = new Employee(userDto.getUserName(), userDto.getPassword(),userDto.getUserType());
        userRepo.save(employee);
    } else if (userDto.getUserType() == User.UserType.MANAGER) {
        Manager manager = new Manager(userDto.getUserName(), userDto.getPassword(),userDto.getUserType());
        userRepo.save(manager);
    }
    logger.info("created {}", userDto);
}

    private void validateUser(UserDto authenticatedUser) {
        if (!isSupervisor(authenticatedUser)) {
            throw new ForbiddenAccessException("Unauthorized");
        }
    }
    private boolean isSupervisor(UserDto userDto) {
        return userDto.getUserType() == User.UserType.SUPERVISOR;
    }

    @Override
    public User verifyUserCredentials(String username, String password) {
        User user = userRepo.getUserByUserName(username);

        if (Objects.isNull(user)) {
            throw new UnauthorizedAccessException("Invalid credentials");
        }
        if (user.getPassword().equals(password)) {
            logger.info("Verified {}", user);
            return user;
        } else {
            logger.error("Not Verified: Passwords do not match for user {}", user);
            throw new UserNotFoundException("");
        }
    }
    @Override
    public UserDto verifyUserCredentials(UsernamePasswordDto usernamePassword) {
        if (Objects.isNull(usernamePassword)) {
            throw new UnauthorizedAccessException("Unauthorized");
        }
        User user = verifyUserCredentials(usernamePassword.getUsername(), usernamePassword.getPassword());
        if (Objects.isNull(user)) {
            throw new UserNotFoundException("User not found");
        }
        return UserDto.fromEntity(user);
    }

    @Override
    public List<User> getAllUsers(UserDto authenticatedUser, User.UserType userType) {
        if (isSupervisor(authenticatedUser)) {
            return userRepo.getUsersByUserType(userType);
        }
        throw new UnauthorizedAccessException("Unauthorized");
    }

}

