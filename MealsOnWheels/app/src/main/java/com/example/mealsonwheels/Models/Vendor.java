package com.example.mealsonwheels.Models;

import java.io.Serializable;
import java.util.Comparator;

public class Vendor implements Serializable {

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
    private String location;
    public static final Comparator<Vendor> BY_RATING = new ByRating();
    public static final Comparator<Vendor> BY_RATING_DESC = new ByRatingDesc();
    public static final Comparator<Vendor> BY_NAME = new ByName();
    public static final Comparator<Vendor> BY_AvgPrice = new ByAvgPrice();
    public static final Comparator<Vendor> BY_AvgPrice_Desc = new ByAvgPriceDesc();


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
                ", location='" + location + '\'' +
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

    public Vendor(String address, String email, String avgPrice, String rating, String noOfRatings, String name, String phone, String openingTime, String closingTime, String type, String location) {
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
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    private static class ByRating implements Comparator<Vendor> {
        public int compare(Vendor v, Vendor w){
            // v.name is a String, and a String object is Comparable
            return v.rating.compareTo(w.rating);
        }
    }

    private static class ByRatingDesc implements Comparator<Vendor> {
        public int compare(Vendor v, Vendor w){
            // v.name is a String, and a String object is Comparable
            return w.rating.compareTo(v.rating);
        }
    }

    private static class ByName implements Comparator<Vendor> {
        public int compare(Vendor v, Vendor w){
            // v.name is a String, and a String object is Comparable
            return v.Name.compareTo(w.Name);
        }
    }

    private static class ByAvgPrice implements Comparator<Vendor> {
        public int compare(Vendor v, Vendor w){
            // v.name is a String, and a String object is Comparable
            return v.avgPrice.compareTo(w.avgPrice);
        }
    }

    private static class ByAvgPriceDesc implements Comparator<Vendor> {
        public int compare(Vendor v, Vendor w){
            // v.name is a String, and a String object is Comparable
            return w.avgPrice.compareTo(v.avgPrice);
        }
    }
}
