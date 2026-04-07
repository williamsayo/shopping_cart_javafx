package org.example.java_shopping_cart.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartItemTest {
    CartItem cartItem;

    @BeforeEach
    void beforeAll() {
        cartItem = new CartItem(2,60,1);
    }

    @Test
    void getPrice() {
       assertEquals(60,cartItem.getPrice());
    }

    @Test
    void getQuantity() {
        assertEquals(2,cartItem.getQuantity());
    }

    @Test
    void getItemNumber() {
        assertEquals(1,cartItem.getItemNumber());
    }

    @Test
    void getSubtotal() {
        assertEquals(120, cartItem.getSubtotal());
    }

    @Test
    void setPrice() {
        cartItem.setPrice(500);
        assertEquals(500,cartItem.getPrice());
    }

    @Test
    void setQuantity() {
        cartItem.setQuantity(5);
        assertEquals(5,cartItem.getQuantity());
    }
}