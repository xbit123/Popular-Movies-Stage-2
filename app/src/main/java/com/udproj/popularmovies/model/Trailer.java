package com.udproj.popularmovies.model;

public class Trailer {
    private String id;
    private String youtube_id;
    private String title;

    public Trailer(String id, String youtube_id, String title) {
        this.id = id;
        this.youtube_id = youtube_id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getYoutube_id() {
        return youtube_id;
    }

    public String getTitle() {
        return title;
    }
}
