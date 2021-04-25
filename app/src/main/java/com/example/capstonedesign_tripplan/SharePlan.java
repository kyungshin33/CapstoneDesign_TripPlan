package com.example.capstonedesign_tripplan;

import com.google.firebase.Timestamp;

import java.util.List;

public class SharePlan {
    private String title;
    private String contents;
    private List<String> images;
    private Timestamp tm;

    public SharePlan(){}

    public SharePlan(String title, String contents, List<String> images, Timestamp tm) {
        this.title = title;
        this.contents = contents;
        this.images = images;
        this.tm = tm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Timestamp getTm() {
        return tm;
    }

    public void setTm(Timestamp tm) {
        this.tm = tm;
    }
}
