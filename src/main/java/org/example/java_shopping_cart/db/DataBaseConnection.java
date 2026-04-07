package org.example.java_shopping_cart.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
        private static final String DB_URL = "jdbc:mariadb://localhost:3306/shopping_cart_localization";
        private static final String DB_USER  = "root";
        private static final String DB_PASS = "root";
        private static Connection connection;

        public static Connection getConnection() throws SQLException {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                System.out.println("Connected to shopping_cart_localization.");
            }
            return connection;
        }
}
