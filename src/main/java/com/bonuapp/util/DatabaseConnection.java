package com.bonuapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Variables en camelCase y constantes en MAYÚSCULAS
    private static final String URL = "jdbc:mysql://localhost:3006/bonuapp_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                System.err.println("Error: Driver JDBC no encontrado. " + e.getMessage());
            }
        }
        return connection;
    }
}
