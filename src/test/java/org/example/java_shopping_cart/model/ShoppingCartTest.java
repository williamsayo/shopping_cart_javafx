package org.example.java_shopping_cart.model;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {
    ShoppingCart shoppingCart = new ShoppingCart();

    @Test
    void testGetItemsReturnsAddedItems() {
        CartItem first = new CartItem(2, 15, 1);
        CartItem second = new CartItem(1, 12.5, 2);

        shoppingCart.addToCart(first);
        shoppingCart.addToCart(second);

        assertEquals(2, shoppingCart.getItems().size());
        assertTrue(shoppingCart.getItems().contains(first));
        assertTrue(shoppingCart.getItems().contains(second));
    }

    @Test
    void testGetTotalItems() {
        assertEquals(0, shoppingCart.getTotalItems());

        shoppingCart.addToCart(new CartItem(3, 20, 1));
        shoppingCart.addToCart(new CartItem(1, 20, 2));
        assertEquals(2, shoppingCart.getTotalItems());

        shoppingCart.removeFromCart(2);
        assertEquals(1, shoppingCart.getTotalItems());
    }

    @Test
    void testCalculateItemCost() {
        assertEquals(60.0,shoppingCart.calculateItemCost(3,20));
    }

    @Test
    void testAddToCart() {
        shoppingCart.addToCart(new CartItem(3,20,1));
        shoppingCart.addToCart(new CartItem(1,20,2));
        assertEquals(80.0,shoppingCart.getCartTotalPrice());
        shoppingCart.addToCart(new CartItem(2,100,3));
        assertEquals(280.0,shoppingCart.getCartTotalPrice());
    }

    @Test
    void testRemoveFromCart() {
        shoppingCart.addToCart(new CartItem(3,20,1));
        shoppingCart.addToCart(new CartItem(1,20,2));
        assertEquals(80.0,shoppingCart.getCartTotalPrice());
        shoppingCart.removeFromCart(2);
        assertEquals(60.0,shoppingCart.getCartTotalPrice());
    }

    @Test
    void formatTotalPrice() {
        shoppingCart.addToCart(new CartItem(1,60,1));
        shoppingCart.addToCart(new CartItem(1,20.99,2));
        assertEquals("$80.99",shoppingCart.formatTotalPrice(Locale.US));
        assertEquals("￥81",shoppingCart.formatTotalPrice(Locale.forLanguageTag("ja-JP")));
        assertEquals("80,99 €",shoppingCart.formatTotalPrice(Locale.forLanguageTag("fi-FI")));
    }

    @Test
    void clearCart() {
        shoppingCart.addToCart(new CartItem(1,60,1));
        shoppingCart.addToCart(new CartItem(1,20.99,2));
        shoppingCart.clearCart();
        assertEquals(0.0,shoppingCart.getCartTotalPrice());
    }
}