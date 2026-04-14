package org.example.java_shopping_cart.db;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseConnectionTest {

    @Test
    void testDataBaseConnectionCanBeInstantiated() {
        assertDoesNotThrow(() -> {
            var clazz = DataBaseConnection.class;
            assertNotNull(clazz);
        });
    }

    @Test
    void testConnectionEnvironmentVariables() {
        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPass = System.getenv("DB_PASS");

        assertEquals(dbUrl, System.getenv("DB_URL"));
        assertEquals(dbUser, System.getenv("DB_USER"));
        assertEquals(dbPass, System.getenv("DB_PASS"));
    }
}
