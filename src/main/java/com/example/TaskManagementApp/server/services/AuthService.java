package com.example.TaskManagementApp.server.services;
import com.example.TaskManagementApp.server.dto.UsernamePasswordDto;
import com.example.TaskManagementApp.server.exception.UnauthorizedAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import java.util.Base64;
import java.util.Objects;

@Service
public class AuthService {
public UsernamePasswordDto extractUsernamePassword(@RequestHeader("Authorization") String authorizationHeader) {
    if (Objects.isNull(authorizationHeader) || !authorizationHeader.startsWith("Basic ")) {
        throw new UnauthorizedAccessException("Invalid or missing Authorization header");
    }
    String credentials = new String(Base64.getDecoder().decode(authorizationHeader.substring(6)));
    String[] parts = credentials.split(":");
    String username = parts[0];
    String password = parts[1];
    return new UsernamePasswordDto(username, password);
}
}
