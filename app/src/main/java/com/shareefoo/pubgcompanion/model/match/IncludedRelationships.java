package com.shareefoo.pubgcompanion.model.match;

import com.google.gson.annotations.SerializedName;

public class IncludedRelationships {

    @SerializedName("participants")
    private Participants participants;

    @SerializedName("team")
    private Team team;

    public Participants getParticipants() {
        return participants;
    }

    public void setParticipants(Participants participants) {
        this.participants = participants;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

}
