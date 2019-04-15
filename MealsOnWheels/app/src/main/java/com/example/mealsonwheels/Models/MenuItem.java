package com.example.mealsonwheels.Models;

import java.io.Serializable;

public class MenuItem implements Serializable {
    private String Name;
    private String ingredients;
    private String isSpicy;
    private String price;
    private String type;
    private String mark;
    private String quantity;

    @Override
    public String toString() {
        return "MenuItem{" +
                "Name='" + Name + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", isspicy='" + isSpicy + '\'' +
                ", priceStr='" + price + '\'' +
                ", type='" + type + '\'' +
                ", mark='" + mark + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }

    public MenuItem() {
    }

    public MenuItem(String name, String ingredients, String isSpicy, String priceStr) {
        Name = name;
        this.ingredients = ingredients;
        this.isSpicy = isSpicy;
        this.price = priceStr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getIsSpicy() {
        return isSpicy;
    }

    public void setIsSpicy(String isSpicy) {
        this.isSpicy = isSpicy;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
