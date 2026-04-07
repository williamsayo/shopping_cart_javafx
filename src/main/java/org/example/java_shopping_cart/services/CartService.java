package org.example.java_shopping_cart.services;

import org.example.java_shopping_cart.db.ShoppingCartDAO;
import org.example.java_shopping_cart.model.ShoppingCart;

import java.sql.Connection;
import java.sql.SQLException;

public class CartService {
    private final ShoppingCartDAO dao;
    private final Connection con;

    public CartService(Connection connection) {
        this.con = connection;
        this.dao = new ShoppingCartDAO(connection);
    }

    public void saveCart(ShoppingCart cart,String language) {
        try {
            int generatedId = dao.saveCartRecord(
                    cart.getTotalItems(),
                    cart.getCartTotalPrice(),
                    language);

            if (generatedId == -1) {
                throw new SQLException("saveCartRecord failure");
            }

            int saved = dao.saveCartItems(generatedId, cart.getItems());
            System.out.println("Saved " + saved + " item(s) for cart id:" + generatedId);
            con.commit();
        } catch (SQLException e) {
            System.err.println("Transaction failed: " + e.getMessage());
        }
    }
}
