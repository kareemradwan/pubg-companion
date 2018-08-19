package com.shareefoo.pubgcompanion.model;

import com.google.gson.annotations.SerializedName;

public class Links {

    @SerializedName("self")
    private String self;

    @SerializedName("schema")
    private String schema;

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

}
