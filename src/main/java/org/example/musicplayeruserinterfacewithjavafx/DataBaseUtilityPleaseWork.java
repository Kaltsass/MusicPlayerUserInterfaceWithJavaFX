package org.example.musicplayeruserinterfacewithjavafx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseUtilityPleaseWork {

    public static Connection getConnection() throws SQLException {
        // Database connection code
        String url = "jdbc:mysql://localhost:3306/javafxdb";  // Fix the URL syntax by removing the extra colon
        String username = "root";
        String password = "MySQL!2024$";
        return DriverManager.getConnection(url, username, password);
    }
}
