package com.example.srcsoftware.Service;

import com.example.srcsoftware.Entity.Teacher;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/report_card_db";
    private final String dbUser = "root";
    private final String dbPassword = "root";

    // -------------------------
    // Get all teachers
    // -------------------------
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teacher";

        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                teachers.add(new Teacher(
                        rs.getString("teacher_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getString("assigned_class"),
                        rs.getBoolean("is_admin")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    // -------------------------
    // Add teacher (manual teacher_id)
    // -------------------------
    public String addTeacher(Teacher teacher) {
        String sql = "INSERT INTO teacher (teacher_id, username, password, full_name, assigned_class, is_admin) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, teacher.getTeacherId());
            ps.setString(2, teacher.getUsername());
            ps.setString(3, teacher.getPassword());
            ps.setString(4, teacher.getFullName());
            ps.setString(5, teacher.getAssignedClass());
            ps.setBoolean(6, teacher.isAdmin());

            ps.executeUpdate();
            return "Teacher added successfully!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error adding teacher: " + e.getMessage();
        }
    }

    // -------------------------
    // Update teacher password
    // -------------------------
    public String updatePassword(String teacherId, String newPassword) {
        String sql = "UPDATE teacher SET password=? WHERE teacher_id=?";

        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setString(2, teacherId);

            int updated = ps.executeUpdate();
            return updated > 0 ? "Password updated successfully!" : "Teacher not found!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error updating password: " + e.getMessage();
        }
    }

    // -------------------------
    // Find teacher by username and password (login)
    // -------------------------
    public Teacher authenticate(String username, String password) {
        String sql = "SELECT * FROM teacher WHERE username=? AND password=?";

        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Teacher(
                            rs.getString("teacher_id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("full_name"),
                            rs.getString("assigned_class"),
                            rs.getBoolean("is_admin")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // authentication failed
    }
}
