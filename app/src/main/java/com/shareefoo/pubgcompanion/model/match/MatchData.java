package com.shareefoo.pubgcompanion.model.match;

import com.google.gson.annotations.SerializedName;
import com.shareefoo.pubgcompanion.model.Links;

public class MatchData {

    @SerializedName("type")
    private String type;

    @SerializedName("id")
    private String id;

    @SerializedName("attributes")
    private MatchAttributes attributes;

    @SerializedName("relationships")
    private MatchRelationships relationships;

    @SerializedName("links")
    private Links links;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MatchAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(MatchAttributes attributes) {
        this.attributes = attributes;
    }

    public MatchRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(MatchRelationships relationships) {
        this.relationships = relationships;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

}
