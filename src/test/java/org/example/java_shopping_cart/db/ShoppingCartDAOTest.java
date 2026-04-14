package org.example.java_shopping_cart.db;

import org.example.java_shopping_cart.model.CartItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ShoppingCartDAOTest {
    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    private ShoppingCartDAO dao;

    @BeforeEach
    void setUp() {
        dao = new ShoppingCartDAO(mockConnection);
    }

    @Test
    void testGetAllLocaleStringsEnglish() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("key"))
                .thenReturn("welcomeMessage", "total");
        when(mockResultSet.getString("value"))
                .thenReturn("Welcome", "Total Cost");

        Map<String, String> result = dao.getAllLocaleStrings("en");

        assertEquals(2, result.size());
        assertEquals("Welcome", result.get("welcomeMessage"));
        assertEquals("Total Cost", result.get("total"));
        verify(mockStatement).setString(1, "en");
        verify(mockStatement).executeQuery();
    }

    @Test
    void testGetAllLocaleStringsEmpty() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        Map<String, String> result = dao.getAllLocaleStrings("fr");

        assertTrue(result.isEmpty());
    }

    @Test
    void testSaveCartRecordSuccessfully() throws SQLException {
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStatement);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(123);

        int cartId = dao.saveCartRecord(5, 250.50, "en");

        assertEquals(123, cartId);
        verify(mockStatement).setInt(1, 5);
        verify(mockStatement).setDouble(2, 250.50);
        verify(mockStatement).setString(3, "en");
        verify(mockStatement).executeUpdate();
    }

    @Test
    void testSaveCartRecordFails() throws SQLException {
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStatement);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        int cartId = dao.saveCartRecord(3, 100.00, "en");

        assertEquals(-1, cartId);
    }

    @Test
    void testSaveCartRecordSQLException() throws SQLException {
        when(mockConnection.prepareStatement(anyString(), anyInt()))
                .thenThrow(new SQLException("Database error"));

        int cartId = dao.saveCartRecord(2, 50.00, "en");

        assertEquals(-1, cartId);
    }

    @Test
    void testSaveCartItemsSuccessfully() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeBatch()).thenReturn(new int[]{1, 1});

        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(2, 30, 1));
        items.add(new CartItem(1, 50, 2));

        int saved = dao.saveCartItems(1, items);

        // Assert
        assertEquals(2, saved);
        verify(mockStatement, times(2)).addBatch();
        verify(mockStatement).executeBatch();
    }

    @Test
    void testSaveCartItemsEmpty() throws SQLException {
        // Arrange
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

        // Act
        int saved = dao.saveCartItems(1, new ArrayList<>());

        // Assert
        assertEquals(0, saved);
    }

    @Test
    void testSaveCartItemsPartialFailure() throws SQLException {
        // Arrange
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeBatch()).thenReturn(new int[]{1, -3, 1});

        List<CartItem> items = new ArrayList<>();
        for (int index = 1; index <= 3; index++) {
            items.add(new CartItem(1, 10.00 * index, index));
        }

        // Act
        int saved = dao.saveCartItems(1, items);

        // Assert
        assertEquals(2, saved);
    }
}
