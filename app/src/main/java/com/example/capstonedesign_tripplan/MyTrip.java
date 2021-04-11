package com.example.capstonedesign_tripplan;

import com.google.firebase.firestore.GeoPoint;

import java.util.List;

public class MyTrip {
    private String title;
    private String date;
    private GeoPoint location;
    private String content;
    private List<String> images;

    public MyTrip() {

    }

    public MyTrip(String title, String date, GeoPoint location, String content, List<String> images) {
        this.title = title;
        this.date = date;
        this.location = location;
        this.content = content;
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}