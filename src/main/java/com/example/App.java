package com.example;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;

import java.sql.Statement;
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// import com.example.DBConn;


/**
 * Author: Luis Velasquez
 * March 2024
 *
 * A simple jdbc program
 *
 */

public class App {
    public static Connection connection = null;

    public static void main(String[] args ) {
        String url = "jdbc:mysql://localhost:3306/school";

        try {
            // Connection
            connection = getConnection(url, "dev", "dev");
            // Statement
            System.out.println("Students:");
            getStudents();
            // PreparedStatement
            System.out.println("Student:");
            getStudent(1);
            connection.close();
            connection = null;

            // Transactions
            connection = getTransactionConnection(url, "dev", "dev");
            insertSubjectAndTeacher();

            // Close
            connection.close();
            connection = null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Connection getConnection(String url, String username, String password) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public static Connection getTransactionConnection(String url, String username, String password) throws SQLException {
        Connection conn = DriverManager.getConnection(url, username, password);
        conn.setAutoCommit(false);
        return conn;
    }
    
    public static void getStudents() throws SQLException {
        String query = "SELECT * FROM students";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        
        while(resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            System.out.println();
            System.out.println("ID: " + String.valueOf(id));
            System.out.println("Name: " + name);
        }
        statement.close();
        resultSet.close();
    }

    public static void getStudent(int id) throws SQLException {
        String query = "SELECT * FROM students WHERE id = ?";
        PreparedStatement preparedStatemtnt = connection.prepareStatement(query);
        preparedStatemtnt.setInt(1, id);
        ResultSet resultSet = preparedStatemtnt.executeQuery();
        while(resultSet.next()) {
            // int id = rs.getInt("id");
            String name = resultSet.getString("name");
            System.out.println();
            System.out.println("ID: " + String.valueOf(id));
            System.out.println("Name: " + name);
        }
        preparedStatemtnt.close();
        resultSet.close();
    }

    public static void insertSubjectAndTeacher() {
        Statement clearSubjectTeacherStatement = null;
        Statement clearSubjectsStatement = null;
        Statement clearTeachersStatement = null;
        PreparedStatement subjectPS = null;
        PreparedStatement teacherPS = null;
        PreparedStatement subjectTeacherPS = null;
        try {
            String clearSubjectTeacherQuery = "DELETE FROM subject_teacher";
            clearSubjectTeacherStatement = connection.createStatement();
            clearSubjectTeacherStatement.executeUpdate(clearSubjectTeacherQuery);

            String clearSubjectsQuery = "DELETE FROM subject";
            clearSubjectsStatement = connection.createStatement();
            clearSubjectsStatement.executeUpdate(clearSubjectsQuery);
            // clearSubjectsStatement.close();

            String clearTeachersQuery = "DELETE FROM teacher";
            clearTeachersStatement = connection.createStatement();
            clearTeachersStatement.executeUpdate(clearTeachersQuery);
            // clearTeachersStatement.close();

            String subjectQuery = "INSERT INTO subject(name) VALUES(?)";
            subjectPS = connection.prepareStatement(subjectQuery);
            subjectPS.setString(1, "Math");
            subjectPS.executeUpdate();
            // subjectPS.close();

            String teacherQuery = "INSERT INTO teacher(name, birthday, dpi) VALUES(?, ?, ?)";
            teacherPS = connection.prepareStatement(teacherQuery);
            teacherPS.setString(1, "Tom");
            LocalDate date = LocalDate.now();
            teacherPS.setObject(2, date);
            teacherPS.setString(3, "1234 56789 0000");
            teacherPS.executeUpdate();
            // teacherPS.close();

            String subjectTeacherQuery = "INSERT INTO subject_teacher(subject_id, teacher_id) VALUES(?, ?)";
            subjectTeacherPS = connection.prepareStatement(subjectTeacherQuery);
            subjectTeacherPS.setInt(1, 1);
            subjectTeacherPS.setInt(2, 1);
            subjectTeacherPS.executeUpdate();
            // subjectTeacherPS.close();

            connection.commit();
        } catch(SQLException e) {
            System.out.println("Transaction error: " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                System.out.println("Rollback error: " + rollbackException.getMessage());
            }
        } finally {
            try {
                clearSubjectTeacherStatement.close();
                clearTeachersStatement.close();
                subjectPS.close();
                teacherPS.close();
                subjectTeacherPS.close();
            } catch (SQLException resourcesException) {
                System.out.println("Resource error: " + resourcesException.getMessage());
            }
        }
    }

}
