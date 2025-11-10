package com.example.srcsoftware.Service;

import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class DatabaseInitializer {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/report_card_db";
    private final String dbUser = "root";
    private final String dbPassword = "root";

    public void init() {
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             Statement stmt = conn.createStatement()) {

            // Teacher table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS teacher (" +
                    "teacher_id VARCHAR(50) PRIMARY KEY," +
                    "username VARCHAR(50) UNIQUE NOT NULL," +
                    "password VARCHAR(100) NOT NULL," +
                    "full_name VARCHAR(100) NOT NULL," +
                    "assigned_class VARCHAR(50)," +
                    "is_admin BOOLEAN NOT NULL)");

            // Default admin
            ResultSet rs = stmt.executeQuery("SELECT * FROM teacher WHERE username='Admin'");
            if (!rs.next()) {
                stmt.executeUpdate("INSERT INTO teacher VALUES (" +
                        "'ADMIN01','Admin','Admin@DBHSOROS','Administrator','-',true)");
            }

            // Class table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS class_info (" +
                    "class_id VARCHAR(50) PRIMARY KEY," +
                    "class_name VARCHAR(50) NOT NULL," +
                    "division_or_stream VARCHAR(50) NOT NULL)");

            // Prepopulate classes
            String[][] classes = {
                    {"NURSERY", "Nursery", "-"},
                    {"JRKG-A", "Jr. Kg", "A"},
                    {"JRKG-B", "Jr. Kg", "B"},
                    {"SRKG-A", "Sr. Kg", "A"},
                    {"SRKG-B", "Sr. Kg", "B"},
                    {"I-A","1","A"},{"I-B","1","B"},
                    {"II-A","2","A"},{"II-B","2","B"},
                    {"III-A","3","A"},{"III-B","3","B"},
                    {"IV-A","4","A"},{"IV-B","4","B"},
                    {"V-A","5","A"},{"V-B","5","B"},
                    {"VI-A","6","A"},{"VI-B","6","B"},
                    {"VII-A","7","A"},{"VII-B","7","B"},
                    {"VIII-A","8","A"},{"VIII-B","8","B"},
                    {"IX-A","9","A"},{"IX-B","9","B"},
                    {"X-A","10","A"},{"X-B","10","B"},
                    {"XI-SCI","11","Science"},{"XI-COM","11","Commerce"},
                    {"XII-SCI","12","Science"},{"XII-COM","12","Commerce"}
            };

            for(String[] c: classes){
                ResultSet rsClass = stmt.executeQuery("SELECT * FROM class_info WHERE class_id='"+c[0]+"'");
                if(!rsClass.next()){
                    stmt.executeUpdate("INSERT INTO class_info VALUES('"+c[0]+"','"+c[1]+"','"+c[2]+"')");
                }
            }

            // Student table with roll_no added
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS student (" +
                    "roll_no VARCHAR(10)," +                       // new column
                    "student_id VARCHAR(50) PRIMARY KEY," +
                    "gr_no VARCHAR(50) UNIQUE," +
                    "full_name VARCHAR(100) NOT NULL," +
                    "class_name VARCHAR(50) NOT NULL," +
                    "division_or_stream VARCHAR(50) NOT NULL," +
                    "dob VARCHAR(20)," +
                    "gender VARCHAR(10)," +
                    "caste VARCHAR(50)," +
                    "category VARCHAR(50)," +
                    "religion VARCHAR(50)," +
                    "blood_grp VARCHAR(10)," +
                    "contact_no VARCHAR(20))");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
