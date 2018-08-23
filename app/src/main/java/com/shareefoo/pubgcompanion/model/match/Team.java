package com.shareefoo.pubgcompanion.model.match;

import com.google.gson.annotations.SerializedName;

public class Team {

    @SerializedName("data")
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
