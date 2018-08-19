package com.shareefoo.pubgcompanion.model;

import com.google.gson.annotations.SerializedName;

public class SeasonAttributes {

    @SerializedName("isCurrentSeason")
    private Boolean isCurrentSeason;

    @SerializedName("isOffseason")
    private Boolean isOffseason;

    public Boolean getIsCurrentSeason() {
        return isCurrentSeason;
    }

    public void setIsCurrentSeason(Boolean isCurrentSeason) {
        this.isCurrentSeason = isCurrentSeason;
    }

    public Boolean getIsOffseason() {
        return isOffseason;
    }

    public void setIsOffseason(Boolean isOffseason) {
        this.isOffseason = isOffseason;
    }

}