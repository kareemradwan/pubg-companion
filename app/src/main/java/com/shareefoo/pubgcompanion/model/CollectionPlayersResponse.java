package com.shareefoo.pubgcompanion.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CollectionPlayersResponse {

    @SerializedName("data")
    private List<PlayerData> data = null;

    @SerializedName("links")
    private Links links;

    @SerializedName("meta")
    private Meta meta;

    public List<PlayerData> getPlayerData() {
        return data;
    }

    public void setPlayerData(List<PlayerData> data) {
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
