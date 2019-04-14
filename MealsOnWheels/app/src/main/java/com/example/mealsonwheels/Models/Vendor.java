package com.example.mealsonwheels.Models;

import java.io.Serializable;

public class Vendor  implements Serializable {

    private String Address;
    private String Email;
    private String avgPrice;
    private String rating;
    private String noOfRatings;
    private String Name;
    private String Phone;
    private String OpeningTime;
    private String ClosingTime;
    private String Type;

    public Vendor() {
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "Address='" + Address + '\'' +
                ", Email='" + Email + '\'' +
                ", avgPrice='" + avgPrice + '\'' +
                ", rating='" + rating + '\'' +
                ", noOfRatings='" + noOfRatings + '\'' +
                ", Name='" + Name + '\'' +
                ", Phone='" + Phone + '\'' +
                ", OpeningTime='" + OpeningTime + '\'' +
                ", ClosingTime='" + ClosingTime + '\'' +
                ", Type='" + Type + '\'' +
                '}';
    }

    public Vendor(String address, String email, String avgPrice, String rating, String noOfRatings, String name, String phone, String openingTime, String closingTime, String type) {
        Address = address;
        Email = email;
        this.avgPrice = avgPrice;
        this.rating = rating;
        this.noOfRatings = noOfRatings;
        Name = name;
        Phone = phone;
        OpeningTime = openingTime;
        ClosingTime = closingTime;
        Type = type;
    }

    public String getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getNoOfRatings() {
        return noOfRatings;
    }

    public void setNoOfRatings(String noOfRatings) {
        this.noOfRatings = noOfRatings;
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


    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
