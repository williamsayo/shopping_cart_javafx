package org.example.java_shopping_cart.model;

public class CartItem {
    private final int itemNumber;
    private int quantity;
    private double price;
    private double subtotal;

    public CartItem(int quantity, double price,int itemNumber) {
        this.quantity = quantity;
        this.price = price;
        this.itemNumber = itemNumber;
        calculateSubtotal();
    }
    public double getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }
    public int getItemNumber() {
        return itemNumber;
    }
    public double getSubtotal() {
        return subtotal;
    }
    public void setPrice(double price) {
        this.price = price;
        calculateSubtotal();
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateSubtotal();
    }

    private void calculateSubtotal() {
        this.subtotal = this.price * this.quantity;
    }

}
