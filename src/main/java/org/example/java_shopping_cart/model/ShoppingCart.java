package org.example.java_shopping_cart.model;

import java.text.NumberFormat;
import java.util.Locale;

public class ShoppingCart {
    double cartTotalPrice = 0.00;

    public double calculateItemCost(int quantity,double price){
        return quantity*price;
    }

    public void addToCart(double total){
        cartTotalPrice += total;
    }

    public double getCartTotalPrice(){
        return cartTotalPrice;
    }

    public String formatTotalPrice(Locale locale){
        return NumberFormat.getCurrencyInstance(locale).format(this.cartTotalPrice);
    }

    public void clearCart(){
        this.cartTotalPrice = 0.0;
    }
}
