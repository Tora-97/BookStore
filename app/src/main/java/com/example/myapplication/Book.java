package com.example.myapplication;

import java.io.Serializable;

public class Book implements Serializable {
    private final int coverId;
    private int id;
    private String title;
    private String author;
    private String imageUrl;
    private int imageResId;
    private String description;
    private double price;

    public Book(String title, String author, int coverId, String description) {
        this.title = title;
        this.author = author;
        this.coverId = coverId;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getCoverId() {
        return coverId;
    }

    public String getCoverUrl() {
        return "https://covers.openlibrary.org/b/id/" + coverId + "-L.jpg";
    }

    public String getDescription() {
        return description;
    }
}
