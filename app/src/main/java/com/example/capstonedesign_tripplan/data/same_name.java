package com.example.capstonedesign_tripplan.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class same_name {
    @SerializedName("region")
    public List<String> region;
    @SerializedName("keyword")
    public String keyword;
    @SerializedName("selected_region")
    public String selected_region;

    public List<String> getRegion() {
        return region;
    }

    public void setRegion(List<String> region) {
        this.region = region;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSelected_region() {
        return selected_region;
    }

    public void setSelected_region(String selected_region) {
        this.selected_region = selected_region;
    }
}
