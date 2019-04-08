package com.example.mealsonwheels;

public class Vendor {
    String Address;
    String Email;
    String Name;
    String Phone;
    int OpeningTime;
    int ClosingTime;
    String Type;

    public Vendor(String address, String email, String name, String phone, int openingTime, int closingTime, String type) {
        Address = address;
        Email = email;
        Name = name;
        Phone = phone;
        OpeningTime = openingTime;
        ClosingTime = closingTime;
        Type = type;
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

    public int getOpeningTime() {
        return OpeningTime;
    }

    public void setOpeningTime(int openingTime) {
        OpeningTime = openingTime;
    }

    public int getClosingTime() {
        return ClosingTime;
    }

    public void setClosingTime(int closingTime) {
        ClosingTime = closingTime;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
