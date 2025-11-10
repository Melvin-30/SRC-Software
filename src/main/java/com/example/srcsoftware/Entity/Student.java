package com.example.srcsoftware.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private String rollNo;        // New field at start (not PK)
    private String studentId;      // PK, manually set
    private String grNo;           // unique
    private String fullName;
    private String className;      // e.g., "Nursery", "Jr. Kg A"
    private String divisionOrStream; // "A/B/-"
    private String dob;
    private String gender;
    private String caste;
    private String category;
    private String religion;
    private String bloodGrp;
    private String contactNo;
}
