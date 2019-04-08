package com.example.mealsonwheels;

public class User {
    private String DeliveryAddress;
    private String Email;
    private String Name;
    private String Phone;

    public User(String deliveryAddress, String email, String name, String phone) {
        DeliveryAddress = deliveryAddress;
        Email = email;
        Name = name;
        Phone = phone;
    }

    public User() {
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

    @Override
    public String toString() {
        return "User{" +
                "DeliveryAddress='" + DeliveryAddress + '\'' +
                ", Email='" + Email + '\'' +
                ", Name='" + Name + '\'' +
                ", Phone='" + Phone + '\'' +
                '}';
    }
}
