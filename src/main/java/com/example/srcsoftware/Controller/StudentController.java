package com.example.srcsoftware.Controller;

import com.example.srcsoftware.Entity.Student;
import com.example.srcsoftware.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // Allow JS frontend access
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // --------------------------
    // Get all students
    // --------------------------
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // --------------------------
    // Add new student
    // --------------------------
    @PostMapping("/add")
    public ResponseEntity<String> addStudent(@RequestBody Student s) {
        String msg = studentService.addStudent(s);
        if(msg.startsWith("Error")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
        return ResponseEntity.ok(msg);
    }

    // --------------------------
    // Update existing student
    // --------------------------
    @PutMapping("/update")
    public ResponseEntity<String> updateStudent(@RequestBody Student s) {
        String msg = studentService.updateStudent(s);
        if(msg.startsWith("Error") || msg.equals("Student not found!")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
        return ResponseEntity.ok(msg);
    }

    // --------------------------
    // Delete a student by GR No
    // --------------------------
    @DeleteMapping("/delete/{grNo}")
    public ResponseEntity<String> deleteStudent(@PathVariable String grNo) {
        String msg = studentService.deleteStudent(grNo);
        if(msg.startsWith("Error") || msg.equals("Student not found!")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
        return ResponseEntity.ok(msg);
    }
}
