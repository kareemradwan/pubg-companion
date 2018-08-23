package com.shareefoo.pubgcompanion.model.match;

import com.google.gson.annotations.SerializedName;

public class MatchAttributes {

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("duration")
    private Integer duration;

    @SerializedName("stats")
    private Object stats;

    @SerializedName("gameMode")
    private String gameMode;

    @SerializedName("shardId")
    private String shardId;

    @SerializedName("tags")
    private Object tags;

    @SerializedName("titleId")
    private String titleId;

    @SerializedName("mapName")
    private String mapName;

    @SerializedName("isCustomMatch")
    private Boolean isCustomMatch;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Object getStats() {
        return stats;
    }

    public void setStats(Object stats) {
        this.stats = stats;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getShardId() {
        return shardId;
    }

    public void setShardId(String shardId) {
        this.shardId = shardId;
    }

    public Object getTags() {
        return tags;
    }

    public void setTags(Object tags) {
        this.tags = tags;
    }

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public Boolean getIsCustomMatch() {
        return isCustomMatch;
    }

    public void setIsCustomMatch(Boolean isCustomMatch) {
        this.isCustomMatch = isCustomMatch;
    }

}
