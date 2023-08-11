// application/AuthenticationService.java
package appication;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationService {
    private Map<String, String> userCredentials;  // Map of username to password

    public AuthenticationService() {
        userCredentials = new HashMap<>();
        userCredentials.put("supervisor", "supervisor"); // Supervisor's credentials
        userCredentials.put("manager", "manager123");       // Manager's credentials
        userCredentials.put("employee", "employee123");     // Employee's credentials
    }


    public boolean authenticate(String username, String password) {
        String storedPassword = userCredentials.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }
}
