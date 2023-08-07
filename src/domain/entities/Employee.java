// Employee.java
package domain.entities;

public class Employee extends User {
    public Employee(int userId, String username) {
        super(userId, username, "Employee");
    }
}
