package com.shareefoo.pubgcompanion.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Assets {

    @SerializedName("data")
    private List<Object> data = null;

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

}
