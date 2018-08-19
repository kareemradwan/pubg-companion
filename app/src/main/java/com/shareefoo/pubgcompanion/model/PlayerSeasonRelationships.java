package com.shareefoo.pubgcompanion.model;

import com.google.gson.annotations.SerializedName;

public class PlayerSeasonRelationships {

    @SerializedName("matchesSquad")
    private MatchesSquad matchesSquad;

    @SerializedName("matchesSquadFPP")
    private MatchesSquadFPP matchesSquadFPP;

    @SerializedName("season")
    private Season season;

    @SerializedName("player")
    private Player player;

    @SerializedName("matchesSolo")
    private MatchesSolo matchesSolo;

    @SerializedName("matchesSoloFPP")
    private MatchesSoloFPP matchesSoloFPP;

    @SerializedName("matchesDuo")
    private MatchesDuo matchesDuo;

    @SerializedName("matchesDuoFPP")
    private MatchesDuoFPP matchesDuoFPP;

    public MatchesSquad getMatchesSquad() {
        return matchesSquad;
    }

    public void setMatchesSquad(MatchesSquad matchesSquad) {
        this.matchesSquad = matchesSquad;
    }

    public MatchesSquadFPP getMatchesSquadFPP() {
        return matchesSquadFPP;
    }

    public void setMatchesSquadFPP(MatchesSquadFPP matchesSquadFPP) {
        this.matchesSquadFPP = matchesSquadFPP;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public MatchesSolo getMatchesSolo() {
        return matchesSolo;
    }

    public void setMatchesSolo(MatchesSolo matchesSolo) {
        this.matchesSolo = matchesSolo;
    }

    public MatchesSoloFPP getMatchesSoloFPP() {
        return matchesSoloFPP;
    }

    public void setMatchesSoloFPP(MatchesSoloFPP matchesSoloFPP) {
        this.matchesSoloFPP = matchesSoloFPP;
    }

    public MatchesDuo getMatchesDuo() {
        return matchesDuo;
    }

    public void setMatchesDuo(MatchesDuo matchesDuo) {
        this.matchesDuo = matchesDuo;
    }

    public MatchesDuoFPP getMatchesDuoFPP() {
        return matchesDuoFPP;
    }

    public void setMatchesDuoFPP(MatchesDuoFPP matchesDuoFPP) {
        this.matchesDuoFPP = matchesDuoFPP;
    }

}
