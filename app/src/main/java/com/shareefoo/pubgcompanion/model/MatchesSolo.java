package com.shareefoo.pubgcompanion.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MatchesSolo {

    @SerializedName("data")
    private List<PlayerSeasonMatchesData> data = null;

    public List<PlayerSeasonMatchesData> getData() {
        return data;
    }

    public void setData(List<PlayerSeasonMatchesData> data) {
        this.data = data;
    }

}
