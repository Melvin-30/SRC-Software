package com.example.srcsoftware.Controller;

import com.example.srcsoftware.Entity.Teacher;
import com.example.srcsoftware.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> credentials) {
        Map<String, Object> response = new HashMap<>();
        String username = credentials.get("username");
        String password = credentials.get("password");

        if(username == null || password == null) {
            response.put("success", false);
            response.put("message", "Username and password are required");
            return response;
        }

        Teacher user = authService.login(username, password);
        if(user != null){
            response.put("success", true);
            response.put("user", user);
        } else {
            response.put("success", false);
            response.put("message", "Invalid username or password");
        }

        return response;
    }
}
