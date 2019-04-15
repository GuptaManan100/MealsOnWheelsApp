package com.example.mealsonwheels.Models;

import java.io.Serializable;
import java.util.HashMap;

public class Order implements Serializable {
    private String customer;
    private String customerLocation;
    private String date;
    private String deliverer;
    private HashMap<String,CartItem> itemsOrdered;
    private String paymentMode;
    private String totalAmount;
    private String transactionId;
    private String vendor;
    private String vendorName;
    private String delivererName;

    public Order() {
    }

    public Order(String customer, String customerLocation, String date, String deliverer, HashMap<String, CartItem> itemsOrdered, String paymentMode, String totalAmount, String transactionId, String vendor, String vendorName, String delivererName) {
        this.customer = customer;
        this.customerLocation = customerLocation;
        this.date = date;
        this.deliverer = deliverer;
        this.itemsOrdered = itemsOrdered;
        this.paymentMode = paymentMode;
        this.totalAmount = totalAmount;
        this.transactionId = transactionId;
        this.vendor = vendor;
        this.vendorName = vendorName;
        this.delivererName = delivererName;
    }

    public String getDelivererName() {
        return delivererName;
    }

    public void setDelivererName(String delivererName) {
        this.delivererName = delivererName;
    }

    @Override
    public String toString() {
        return "Order{" +
                "customer='" + customer + '\'' +
                ", customerLocation='" + customerLocation + '\'' +
                ", date='" + date + '\'' +
                ", deliverer='" + deliverer + '\'' +
                ", itemsOrdered=" + itemsOrdered.toString() +
                ", paymentMode='" + paymentMode + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", vendor='" + vendor + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", delivererName='" + delivererName + '\'' +
                '}';
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomerLocation() {
        return customerLocation;
    }

    public void setCustomerLocation(String customerLocation) {
        this.customerLocation = customerLocation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeliverer() {
        return deliverer;
    }

    public void setDeliverer(String deliverer) {
        this.deliverer = deliverer;
    }

    public HashMap<String, CartItem> getItemsOrdered() {
        return itemsOrdered;
    }

    public void setItemsOrdered(HashMap<String, CartItem> itemsOrdered) {
        this.itemsOrdered = itemsOrdered;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
}
