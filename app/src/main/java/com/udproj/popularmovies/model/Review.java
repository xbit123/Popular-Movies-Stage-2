package com.udproj.popularmovies.model;

public class Review {
    private String id;
    private String author;
    private String content;

    public Review(String author, String content, String id) {
        this.author = author;
        this.content = content;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
