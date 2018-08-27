package com.shareefoo.pubgcompanion.model.map;

public class PlayerLocation {

    public String playerName;
    public double playerLatitude;
    public double playerLongitude;

    public PlayerLocation() {
        // Default constructor required for calls to DataSnapshot.getValue(PlayerLocation.class)
    }

    public PlayerLocation(String playerName, double playerLatitude, double playerLongitude) {
        this.playerName = playerName;
        this.playerLatitude = playerLatitude;
        this.playerLongitude = playerLongitude;
    }

}