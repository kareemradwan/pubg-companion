package com.shareefoo.pubgcompanion.model.match;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MatchAssets {

    @SerializedName("data")
    private List<MatchRelationshipsData> data = null;

    public List<MatchRelationshipsData> getData() {
        return data;
    }

    public void setData(List<MatchRelationshipsData> data) {
        this.data = data;
    }

}
