package com.example.mealsonwheels.Models;

import java.io.Serializable;

public class Deliverer implements Serializable {
    private String Address;
    private String Email;
    private String Name;
    private String Phone;
    private String IsFree;

    public Deliverer(String address, String email, String name, String phone, String isFree) {
        Address = address;
        Email = email;
        Name = name;
        Phone = phone;
        IsFree = isFree;
    }

    public Deliverer() {
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
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

    public String getIsFree() {
        return IsFree;
    }

    public void setIsFree(String isFree) {
        IsFree = isFree;
    }

    @Override
    public String toString() {
        return "Deliverer{" +
                "Address='" + Address + '\'' +
                ", Email='" + Email + '\'' +
                ", Name='" + Name + '\'' +
                ", Phone='" + Phone + '\'' +
                ", IsFree='" + IsFree + '\'' +
                '}';
    }
}

