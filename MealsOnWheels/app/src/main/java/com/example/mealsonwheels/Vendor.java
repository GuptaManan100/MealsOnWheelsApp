package com.example.mealsonwheels;

import java.io.Serializable;

public class Vendor implements Serializable {
    private String Address;
    private String Email;
    private String Name;
    private String Phone;
    private String OpeningTime;
    private String ClosingTime;
    private String Type;

    public Vendor() {
    }

    public Vendor(String address, String email, String name, String phone, String openingTime, String closingTime, String type) {
        Address = address;
        Email = email;
        Name = name;
        Phone = phone;
        OpeningTime = openingTime;
        ClosingTime = closingTime;
        Type = type;
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "Address='" + Address + '\'' +
                ", Email='" + Email + '\'' +
                ", Name='" + Name + '\'' +
                ", Phone='" + Phone + '\'' +
                ", OpeningTime=" + OpeningTime +
                ", ClosingTime=" + ClosingTime +
                ", Type='" + Type + '\'' +
                '}';
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

    public String getOpeningTime() {
        return OpeningTime;
    }

    public void setOpeningTime(String openingTime) {
        OpeningTime = openingTime;
    }

    public String getClosingTime() {
        return ClosingTime;
    }

    public void setClosingTime(String closingTime) {
        ClosingTime = closingTime;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
