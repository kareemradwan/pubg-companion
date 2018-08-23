package com.shareefoo.pubgcompanion.model.match;

import com.google.gson.annotations.SerializedName;
import com.shareefoo.pubgcompanion.model.Links;

import java.util.List;

public class MatchResponse {

    @SerializedName("data")
    private MatchData data;

    @SerializedName("included")
    private List<MatchIncluded> included = null;

    @SerializedName("links")
    private Links links;

    @SerializedName("meta")
    private Meta meta;

    public MatchData getData() {
        return data;
    }

    public void setData(MatchData data) {
        this.data = data;
    }

    public List<MatchIncluded> getIncluded() {
        return included;
    }

    public void setIncluded(List<MatchIncluded> included) {
        this.included = included;
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
