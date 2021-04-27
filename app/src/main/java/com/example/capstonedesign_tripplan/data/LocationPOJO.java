package com.example.capstonedesign_tripplan.data;

import com.example.capstonedesign_tripplan.documents;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationPOJO {
    @SerializedName("meta")
    public Meta meta;
    @SerializedName("documents")
    public List<documents> documents;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<documents> getDocuments() {
        return documents;
    }

    public void setDocuments(List<documents> documents) {
        this.documents = documents;
    }
}
