package org.example.java_shopping_cart.model;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {
    ShoppingCart shoppingCart = new ShoppingCart();

    @Test
    void testCalculateItemCost() {
        assertEquals(60.0,shoppingCart.calculateItemCost(3,20));
    }

    @Test
    void testAddToCart() {
        shoppingCart.addToCart(60);
        shoppingCart.addToCart(20);
        assertEquals(80.0,shoppingCart.getCartTotalPrice());
        shoppingCart.addToCart(200);
        assertEquals(280.0,shoppingCart.getCartTotalPrice());
    }

    @Test
    void formatTotalPrice() {
        shoppingCart.addToCart(60);
        shoppingCart.addToCart(20.99);
        assertEquals("$80.99",shoppingCart.formatTotalPrice(Locale.US));
        assertEquals("￥81",shoppingCart.formatTotalPrice(new Locale("ja","JP")));
        assertEquals("80,99 €",shoppingCart.formatTotalPrice(new Locale("fi","FI")));
    }

    @Test
    void clearCart() {
        shoppingCart.addToCart(60);
        shoppingCart.addToCart(20);
        shoppingCart.clearCart();
        assertEquals(0.0,shoppingCart.getCartTotalPrice());
    }
}