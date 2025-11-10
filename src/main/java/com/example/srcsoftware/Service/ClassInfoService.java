package com.example.srcsoftware.Service;

import org.springframework.stereotype.Service;
import com.example.srcsoftware.Entity.ClassInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClassInfoService {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/report_card_db";
    private final String dbUser = "root";
    private final String dbPassword = "root";

    public List<ClassInfo> getAllClasses() {
        List<ClassInfo> classes = new ArrayList<>();
        String sql = "SELECT * FROM class_info ORDER BY class_name, division_or_stream";

        try(Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            while(rs.next()){
                classes.add(new ClassInfo(
                        rs.getString("class_id"),
                        rs.getString("class_name"),
                        rs.getString("division_or_stream")
                ));
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
        return classes;
    }
}
