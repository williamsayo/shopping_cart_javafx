package org.example.java_shopping_cart.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartItemTest {
    private CartItem cartItem;

    @BeforeEach
    void setUp() {
        cartItem = new CartItem(2,60,1);
    }

    @Test
    void testConstructor() {
        assertEquals(2, cartItem.getQuantity());
        assertEquals(60, cartItem.getPrice());
        assertEquals(1, cartItem.getItemNumber());
        assertEquals(120, cartItem.getSubtotal());
    }

    @Test
    void testConstructorWithZeroQuantity() {
        CartItem zeroQtyItem = new CartItem(0, 50, 2);
        assertEquals(0, zeroQtyItem.getQuantity());
        assertEquals(50, zeroQtyItem.getPrice());
        assertEquals(0, zeroQtyItem.getSubtotal());
    }

    @Test
    void testConstructorWithZeroPrice() {
        CartItem zeroPriceItem = new CartItem(5, 0, 3);
        assertEquals(5, zeroPriceItem.getQuantity());
        assertEquals(0, zeroPriceItem.getPrice());
        assertEquals(0, zeroPriceItem.getSubtotal());
    }

    @Test
    void testGetPrice() {
       assertEquals(60,cartItem.getPrice());
    }

    @Test
    void testGetQuantity() {
        assertEquals(2,cartItem.getQuantity());
    }

    @Test
    void testGetItemNumber() {
        assertEquals(1,cartItem.getItemNumber());
    }

    @Test
    void testGetSubtotal() {
        assertEquals(120, cartItem.getSubtotal());
    }

    @Test
    void testSetPrice() {
        cartItem.setPrice(500);
        assertEquals(500,cartItem.getPrice());
        assertEquals(1000, cartItem.getSubtotal());
    }

    @Test
    void testSetPriceToZero() {
        cartItem.setPrice(0);
        assertEquals(0, cartItem.getPrice());
        assertEquals(0, cartItem.getSubtotal());
    }

    @Test
    void testSetPriceDecimal() {
        cartItem.setPrice(29.99);
        assertEquals(29.99, cartItem.getPrice());
        assertEquals(59.98, cartItem.getSubtotal(), 0.01);
    }

    @Test
    void testSetQuantity() {
        cartItem.setQuantity(5);
        assertEquals(5,cartItem.getQuantity());
        assertEquals(300, cartItem.getSubtotal());
    }

    @Test
    void testSetQuantityToOne() {
        cartItem.setQuantity(1);
        assertEquals(1, cartItem.getQuantity());
        assertEquals(60, cartItem.getSubtotal());
    }

    @Test
    void testSetQuantityToZero() {
        cartItem.setQuantity(0);
        assertEquals(0, cartItem.getQuantity());
        assertEquals(0, cartItem.getSubtotal());
    }

    @Test
    void testSetPriceAndQuantityTogether() {
        cartItem.setPrice(25);
        cartItem.setQuantity(4);
        assertEquals(25, cartItem.getPrice());
        assertEquals(4, cartItem.getQuantity());
        assertEquals(100, cartItem.getSubtotal());
    }

    @Test
    void testMultiplePriceUpdates() {
        cartItem.setPrice(10);
        assertEquals(20, cartItem.getSubtotal());
        
        cartItem.setPrice(15);
        assertEquals(30, cartItem.getSubtotal());
        
        cartItem.setPrice(20);
        assertEquals(40, cartItem.getSubtotal());
    }

    @Test
    void testMultipleQuantityUpdates() {
        cartItem.setQuantity(1);
        assertEquals(60, cartItem.getSubtotal());
        
        cartItem.setQuantity(3);
        assertEquals(180, cartItem.getSubtotal());
        
        cartItem.setQuantity(10);
        assertEquals(600, cartItem.getSubtotal());
    }

    @Test
    void testLargeValues() {
        CartItem largeItem = new CartItem(1000, 9999.99, 100);
        assertEquals(1000, largeItem.getQuantity());
        assertEquals(9999.99, largeItem.getPrice());
        assertEquals(9999990, largeItem.getSubtotal(), 0.01);
    }

    @Test
    void testSmallDecimalValues() {
        CartItem smallItem = new CartItem(100, 0.01, 50);
        assertEquals(100, smallItem.getQuantity());
        assertEquals(0.01, smallItem.getPrice());
        assertEquals(1.0, smallItem.getSubtotal(), 0.01);
    }

    @Test
    void testItemNumberImmutable() {
        int originalItemNumber = cartItem.getItemNumber();
        cartItem.setPrice(100);
        cartItem.setQuantity(10);
        assertEquals(originalItemNumber, cartItem.getItemNumber());
    }
}