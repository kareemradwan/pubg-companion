package com.shareefoo.pubgcompanion.model;

import com.google.gson.annotations.SerializedName;

public class Relationships {

    @SerializedName("matches")
    private Matches matches;

    @SerializedName("assets")
    private Assets assets;

    public Matches getMatches() {
        return matches;
    }

    public void setMatches(Matches matches) {
        this.matches = matches;
    }

    public Assets getAssets() {
        return assets;
    }

    public void setAssets(Assets assets) {
        this.assets = assets;
    }

}
