package com.shareefoo.pubgcompanion.model.match;

import com.google.gson.annotations.SerializedName;

public class MatchRelationships {

    @SerializedName("rosters")
    private MatchRosters rosters;

    @SerializedName("assets")
    private MatchAssets assets;

    public MatchRosters getRosters() {
        return rosters;
    }

    public void setRosters(MatchRosters rosters) {
        this.rosters = rosters;
    }

    public MatchAssets getAssets() {
        return assets;
    }

    public void setAssets(MatchAssets assets) {
        this.assets = assets;
    }

}
