package com.shareefoo.pubgcompanion.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeasonResponse {

    @SerializedName("data")
    private List<SeasonData> data = null;

    @SerializedName("links")
    private Links links;

    @SerializedName("meta")
    private Meta meta;

    public List<SeasonData> getSeasonData() {
        return data;
    }

    public void setSeasonData(List<SeasonData> data) {
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
