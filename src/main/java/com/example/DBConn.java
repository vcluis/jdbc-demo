package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
    public static String url;
    public static Connection conn;

    static {
        String url = "jdbc:mysql://localhost:3306/school";
        try {
            conn = DriverManager.getConnection(url, "dev", "dev");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void closeConnection() throws SQLException {
        conn.close();
    }

}
