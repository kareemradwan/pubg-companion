package com.shareefoo.pubgcompanion.model.match;

import com.google.gson.annotations.SerializedName;

public class MatchIncluded {

    @SerializedName("type")
    private String type;

    @SerializedName("id")
    private String id;

    @SerializedName("attributes")
    private IncludedAttributes attributes;

    @SerializedName("relationships")
    private IncludedRelationships relationships;

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

    public IncludedAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(IncludedAttributes attributes) {
        this.attributes = attributes;
    }

    public IncludedRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(IncludedRelationships relationships) {
        this.relationships = relationships;
    }

}
