package com.shareefoo.pubgcompanion.model;

import com.google.gson.annotations.SerializedName;

public class PlayerSeasonResponse {

    @SerializedName("data")
    private PlayerSeasonData data;

    @SerializedName("links")
    private Links links;

    @SerializedName("meta")
    private Meta meta;

    public PlayerSeasonData getPlayerSeasonData() {
        return data;
    }

    public void setPlayerSeasonData(PlayerSeasonData data) {
        this.data = data;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}
