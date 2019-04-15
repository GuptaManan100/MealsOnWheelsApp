package com.example.mealsonwheels.Models;

import java.io.Serializable;

public class CartItem implements Serializable {

    private String price;
    private String quantity;

    public CartItem() {
    }

    @Override
    public String toString() {
        return "{Price=" + price +", Quantity='" + quantity +'}';
    }

    public CartItem(String price, String quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}

