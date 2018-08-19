package com.shareefoo.pubgcompanion.model;

import com.google.gson.annotations.SerializedName;

public class PlayerSeasonData {

    @SerializedName("type")
    private String type;

    @SerializedName("attributes")
    private PlayerSeasonAttributes attributes;

    @SerializedName("relationships")
    private PlayerSeasonRelationships relationships;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PlayerSeasonAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(PlayerSeasonAttributes attributes) {
        this.attributes = attributes;
    }

    public PlayerSeasonRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(PlayerSeasonRelationships relationships) {
        this.relationships = relationships;
    }

}
