package com.example.srcsoftware.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    private String teacherId; // manually set
    private String username;
    private String password;
    private String fullName;
    private String assignedClass; // "-" if admin
    private boolean isAdmin; // true for admin
}
