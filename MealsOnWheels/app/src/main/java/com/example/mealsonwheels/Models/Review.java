package com.example.mealsonwheels.Models;

import java.io.Serializable;

public class Review  implements Serializable {
    private String rating;
    private String review;
    private String customer;
    private String vendor;

    public Review() {
    }

    public Review(String rating, String review, String customer, String vendor) {
        this.rating = rating;
        this.review = review;
        this.customer = customer;
        this.vendor = vendor;
    }

    @Override
    public String toString() {
        return "Review{" +
                "rating='" + rating + '\'' +
                ", review='" + review + '\'' +
                ", customer='" + customer + '\'' +
                ", vendor='" + vendor + '\'' +
                '}';
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
}
