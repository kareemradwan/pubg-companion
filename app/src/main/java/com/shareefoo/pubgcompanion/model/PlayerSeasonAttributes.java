package com.shareefoo.pubgcompanion.model;

import com.google.gson.annotations.SerializedName;

public class PlayerSeasonAttributes {

    @SerializedName("gameModeStats")
    private GameModeStats gameModeStats;

    public GameModeStats getGameModeStats() {
        return gameModeStats;
    }

    public void setGameModeStats(GameModeStats gameModeStats) {
        this.gameModeStats = gameModeStats;
    }

}
