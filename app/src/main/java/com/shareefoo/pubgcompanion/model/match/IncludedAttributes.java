package com.shareefoo.pubgcompanion.model.match;

import com.google.gson.annotations.SerializedName;

public class IncludedAttributes {

    @SerializedName("stats")
    private AttributesStats stats;

    @SerializedName("actor")
    private String actor;

    @SerializedName("shardId")
    private String shardId;

    @SerializedName("won")
    private String won;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("URL")
    private String uRL;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    public AttributesStats getStats() {
        return stats;
    }

    public void setStats(AttributesStats stats) {
        this.stats = stats;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getShardId() {
        return shardId;
    }

    public void setShardId(String shardId) {
        this.shardId = shardId;
    }

    public String getWon() {
        return won;
    }

    public void setWon(String won) {
        this.won = won;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getURL() {
        return uRL;
    }

    public void setURL(String uRL) {
        this.uRL = uRL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
