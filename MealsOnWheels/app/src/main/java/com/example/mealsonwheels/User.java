package com.example.mealsonwheels;

public class User {
    String DeliveryAddress;
    String Email;
    String Name;
    String Phone;

    public User(String deliveryAddress, String email, String name, String phone) {
        DeliveryAddress = deliveryAddress;
        Email = email;
        Name = name;
        Phone = phone;
    }

    public String getDeliveryAddress() {
        return DeliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        DeliveryAddress = deliveryAddress;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
