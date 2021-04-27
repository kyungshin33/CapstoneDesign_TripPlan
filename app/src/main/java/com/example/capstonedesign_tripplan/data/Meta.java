package com.example.capstonedesign_tripplan.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Meta {
    @SerializedName("same_name")
    public same_name same_name;
    @SerializedName("pageable_count")
    public int pageable_count;
    @SerializedName("total_count")
    public int total_count;
    @SerializedName("is_end")
    public boolean is_end;

    public same_name getSame_name() {
        return same_name;
    }

    public void setSame_name(same_name same_name) {
        this.same_name = same_name;
    }

    public int getPageable_count() {
        return pageable_count;
    }

    public void setPageable_count(int pageable_count) {
        this.pageable_count = pageable_count;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public boolean isIs_end() {
        return is_end;
    }

    public void setIs_end(boolean is_end) {
        this.is_end = is_end;
    }
}

