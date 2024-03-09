package com.example;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.example.DBConn;

/**
 * Author: Luis Velasquez
 * March 2024
 *
 * A simple jdbc program
 *
 */

public class App {
    public static void main(String[] args ) {
        String url = "jdbc:mysql://localhost:3306/school";
        Connection conn = getConnection(url, "dev", "dev");

        try {
            System.out.println("Students:");
            getStudents();
            System.out.println("Student:");
            getStudent(1);
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("This is the end");
    }

    public static Connection getConnection(String url, String username, String password) {
        Connection conn = null;
        try {
        	conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
        	e.getMessage();
        }
        return conn;
    }
    
    public static void getStudents() throws SQLException {
        String query = "SELECT * FROM students";
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery(query);
        
        while(rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            System.out.println();
            System.out.println("ID: " + String.valueOf(id));
            System.out.println("Name: " + name);
        }
        s.close();
        rs.close();
    }

    public static void getStudent(int id) throws SQLException {
        String query = "SELECT * FROM students WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            // int id = rs.getInt("id");
            String name = rs.getString("name");
            System.out.println();
            System.out.println("ID: " + String.valueOf(id));
            System.out.println("Name: " + name);
        }
        ps.close();
        rs.close();
    }

}
