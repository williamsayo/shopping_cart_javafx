package org.example.java_shopping_cart.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
        private static final String DB_URL = System.getenv("DB_URL");
        private static final String DB_USER  = System.getenv("DB_USER");
        private static final String DB_PASS = System.getenv("DB_PASS");
        private static Connection connection;

        private DataBaseConnection() {}

        public static Connection getConnection() throws SQLException {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                System.out.println("Connected to shopping_cart_localization.");
            }
            return connection;
        }
}
