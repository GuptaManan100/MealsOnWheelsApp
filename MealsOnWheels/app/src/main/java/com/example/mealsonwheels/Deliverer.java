package com.example.mealsonwheels;

public class Deliverer {
    String Address;
    String Email;
    String Name;
    String Phone;
    String IsFree;

    public Deliverer(String address, String email, String name, String phone, String isFree) {
        Address = address;
        Email = email;
        Name = name;
        Phone = phone;
        IsFree = isFree;
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
}

