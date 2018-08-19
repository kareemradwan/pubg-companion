package com.shareefoo.pubgcompanion.model;

import com.google.gson.annotations.SerializedName;

public class SinglePlayerResponse {

    @SerializedName("data")
    private PlayerData data;

    @SerializedName("links")
    private Links links;

    @SerializedName("meta")
    private Meta meta;

    public PlayerData getPlayerData() {
        return data;
    }

    public void setPlayerData(PlayerData playerData) {
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
