package com.example.srcsoftware.Service;

import com.example.srcsoftware.Entity.Teacher;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class AuthService {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/report_card_db";
    private final String dbUser = "root";
    private final String dbPassword = "root";

    public Teacher login(String username, String password) {
        String sql = "SELECT * FROM teacher WHERE username=? AND password=?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Teacher(
                        rs.getString("teacher_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getString("assigned_class"),
                        rs.getBoolean("is_admin")
                );
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
