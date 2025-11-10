package com.example.srcsoftware.Service;

import com.example.srcsoftware.Entity.Student;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/report_card_db";
    private final String dbUser = "root";
    private final String dbPassword = "root";

    // Fetch all students ordered by class_name, division_or_stream, roll_no
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student ORDER BY class_name, division_or_stream, roll_no";

        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Student(
                        rs.getString("roll_no"),          // rollNo
                        rs.getString("student_id"),
                        rs.getString("gr_no"),
                        rs.getString("full_name"),
                        rs.getString("class_name"),
                        rs.getString("division_or_stream"),
                        rs.getString("dob"),
                        rs.getString("gender"),
                        rs.getString("caste"),
                        rs.getString("category"),
                        rs.getString("religion"),
                        rs.getString("blood_grp"),
                        rs.getString("contact_no")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Add new student
    public String addStudent(Student s) {
        String sql = "INSERT INTO student (roll_no, student_id, gr_no, full_name, class_name, division_or_stream, dob, gender, caste, category, religion, blood_grp, contact_no) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getRollNo());
            ps.setString(2, s.getStudentId());
            ps.setString(3, s.getGrNo());
            ps.setString(4, s.getFullName());
            ps.setString(5, s.getClassName());
            ps.setString(6, s.getDivisionOrStream());
            ps.setString(7, s.getDob());
            ps.setString(8, s.getGender());
            ps.setString(9, s.getCaste());
            ps.setString(10, s.getCategory());
            ps.setString(11, s.getReligion());
            ps.setString(12, s.getBloodGrp());
            ps.setString(13, s.getContactNo());

            ps.executeUpdate();
            return "Student added successfully!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    // Update student based on gr_no
    public String updateStudent(Student s) {
        String sql = "UPDATE student SET roll_no=?, full_name=?, class_name=?, division_or_stream=?, dob=?, gender=?, caste=?, category=?, religion=?, blood_grp=?, contact_no=? " +
                "WHERE gr_no=?";

        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getRollNo());
            ps.setString(2, s.getFullName());
            ps.setString(3, s.getClassName());
            ps.setString(4, s.getDivisionOrStream());
            ps.setString(5, s.getDob());
            ps.setString(6, s.getGender());
            ps.setString(7, s.getCaste());
            ps.setString(8, s.getCategory());
            ps.setString(9, s.getReligion());
            ps.setString(10, s.getBloodGrp());
            ps.setString(11, s.getContactNo());
            ps.setString(12, s.getGrNo());

            int updated = ps.executeUpdate();
            return updated > 0 ? "Student updated successfully!" : "Student not found!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error updating student: " + e.getMessage();
        }
    }

    // Delete student based on gr_no
    public String deleteStudent(String grNo) {
        String sql = "DELETE FROM student WHERE gr_no=?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, grNo);
            int deleted = ps.executeUpdate();
            return deleted > 0 ? "Student deleted successfully!" : "Student not found!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error deleting student: " + e.getMessage();
        }
    }
}
