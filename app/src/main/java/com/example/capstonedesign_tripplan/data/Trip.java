package com.example.capstonedesign_tripplan.data;

import com.google.firebase.firestore.GeoPoint;

import java.util.Date;
import java.util.List;

public class Trip {
    private int index;
    private String title;
    private String name; //구글아이디
    private String email;
    private GeoPoint location;
    private String date;
    private String content;
    private List<String> images;

    public Trip() {

    }

    public Trip(int index, String title, String name, String email, GeoPoint location, String date, String content, List<String> images) {
        this.index = index;
        this.title = title;
        this.name = name;
        this.email = email;
        this.location = location;
        this.date = date;
        this.content = content;
        this.images = images;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
