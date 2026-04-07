package org.example.java_shopping_cart.model;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShoppingCart {
    private final List<CartItem> items = new ArrayList<>();

    public void addToCart(CartItem item) {
        this.items.add(item);
    }

    public void removeFromCart(int itemNumber) {
        this.items.removeIf(item -> item.getItemNumber() == itemNumber);
    }

    public List<CartItem> getItems() {
        return items;
    }

    public int getTotalItems() {
        return this.items.size();
    }

    public double calculateItemCost(int quantity,double price){
        return quantity*price;
    }

    public double getCartTotalPrice(){
        return calculateTotalPrice();
    }

    public String formatTotalPrice(Locale locale){
        return NumberFormat.getCurrencyInstance(locale).format(calculateTotalPrice());
    }

    private double calculateTotalPrice(){
        double cartTotalPrice = 0.00;
        for (CartItem item : this.items) {
            cartTotalPrice += item.getSubtotal();
        }
        return cartTotalPrice;
    }

    public void clearCart(){
        items.clear();
    }
}
