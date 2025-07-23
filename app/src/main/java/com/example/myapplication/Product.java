package com.example.myapplication;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String author;
    private String imageUrl;
    private String description;
    private int imageResId;
    private double price;

    public Product(String name, String author, String imageUrl, String description, int imageResId, double price) {
        this.name = name;
        this.author = author;
        this.imageUrl = imageUrl;
        this.description = description;
        this.imageResId = imageResId;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public double getPrice() {
        return price;
    }
}
