package com.shareefoo.pubgcompanion.model;

import com.google.gson.annotations.SerializedName;

public class GameModeStats {

    @SerializedName("duo")
    private Duo duo;

    @SerializedName("duo-fpp")
    private DuoFpp duoFpp;

    @SerializedName("solo")
    private Solo solo;

    @SerializedName("solo-fpp")
    private SoloFpp soloFpp;

    @SerializedName("squad")
    private Squad squad;

    @SerializedName("squad-fpp")
    private SquadFpp squadFpp;

    public Duo getDuo() {
        return duo;
    }

    public void setDuo(Duo duo) {
        this.duo = duo;
    }

    public DuoFpp getDuoFpp() {
        return duoFpp;
    }

    public void setDuoFpp(DuoFpp duoFpp) {
        this.duoFpp = duoFpp;
    }

    public Solo getSolo() {
        return solo;
    }

    public void setSolo(Solo solo) {
        this.solo = solo;
    }

    public SoloFpp getSoloFpp() {
        return soloFpp;
    }

    public void setSoloFpp(SoloFpp soloFpp) {
        this.soloFpp = soloFpp;
    }

    public Squad getSquad() {
        return squad;
    }

    public void setSquad(Squad squad) {
        this.squad = squad;
    }

    public SquadFpp getSquadFpp() {
        return squadFpp;
    }

    public void setSquadFpp(SquadFpp squadFpp) {
        this.squadFpp = squadFpp;
    }

}
