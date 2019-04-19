package com.example.mealsonwheels.Models;

import java.util.HashMap;
import java.util.Iterator;

public class DelivererHistory {

    public String date;
    public String customer;
    public String vendorName;
    public String transactionId;
    public String totalAmount;
    public String paymentMode;
    private HashMap<String,CartItem> itemsOrdered;
    public String item;
    public String qty;
    public String price;
    //public String itemsOrdered;

    public String getDate() {
        return date;
    }
    public String getCustomer(){
        return customer;
    }
    public String getVendorName() {
        return vendorName;
    }
    public String getTransactionId() {
        return transactionId;
    }
    public String getTotalAmount() {
        return totalAmount;
    }
    public String getPaymentMode() {
        return paymentMode;
    }
    public HashMap<String, CartItem> getItemsOrdered() {
        return itemsOrdered;
    }
    public void setItemsOrdered(HashMap<String, CartItem> itemsOrdered) {
        this.itemsOrdered = itemsOrdered;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public void setCustomer(String customer) {
        this.customer = customer;
    }
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }
}