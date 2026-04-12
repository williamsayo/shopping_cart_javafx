package org.example.java_shopping_cart.db;

import org.example.java_shopping_cart.model.CartItem;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShoppingCartDAO {
    private final Connection con;
    private static final Logger logger = Logger.getLogger(ShoppingCartDAO.class.getName());
    public ShoppingCartDAO(Connection con) {
        this.con = con;
    }

    public Map<String, String> getAllLocaleStrings(String language) {
        Map<String, String> strings = new HashMap<>();
        String query = "SELECT `key`, value FROM localization_strings WHERE language = ?";

        try (PreparedStatement statement = this.con.prepareStatement(query)) {
            statement.setString(1, language);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                strings.put(resultSet.getString("key"), resultSet.getString("value"));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

        // Fallback
        if (strings.isEmpty() && !"en".equalsIgnoreCase(language)) {
            logger.log(Level.SEVERE,"No strings for {0}", language);
            return getAllLocaleStrings("en");
        }

        return strings;
    }

    public int saveCartItems(int cartId, List<CartItem> items) {
        String query = "INSERT INTO cart_items (cart_record_id, item_number, price, quantity, subtotal) VALUES (?, ?, ?, ?, ?)";

        int insertCount = 0;

        try (PreparedStatement statement = con.prepareStatement(query)) {

            for (CartItem item : items) {
                statement.setInt(1, cartId);
                statement.setInt(2, item.getItemNumber());
                statement.setDouble(3, item.getPrice());
                statement.setInt(4, item.getQuantity());
                statement.setDouble(5, item.getSubtotal());
                statement.addBatch();
            }

            int[] results = statement.executeBatch();
            for (int result : results) {
                if (result > 0) insertCount++;
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE,"saveCartItems failed: {0}", e.getMessage());
        }
        return insertCount;
    }

    public int saveCartRecord(int totalItems, double totalCost, String language) {
        String query = "INSERT INTO cart_records (total_items, total_cost, language) VALUES (?, ?, ?)";

        try (PreparedStatement statement = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, totalItems);
            statement.setDouble(2, totalCost);
            statement.setString(3, language);
            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE,"saveCartItems failed: {0}", e.getMessage());
        }
        return -1;
    }
}
