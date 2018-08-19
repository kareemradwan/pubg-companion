package com.shareefoo.pubgcompanion.api;

import com.shareefoo.pubgcompanion.model.CollectionPlayersResponse;
import com.shareefoo.pubgcompanion.model.PlayerSeasonResponse;
import com.shareefoo.pubgcompanion.model.SeasonResponse;
import com.shareefoo.pubgcompanion.model.SinglePlayerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by shareefoo
 */

public interface ApiInterface {

    //    @GET("matches/{id}")
//    Call<Match> getMatchById(@Path("id") String id);
//
    @GET("players/{id}")
    Call<SinglePlayerResponse> getPlayerById(@Path("id") String id);

    @GET("players?filter[playerNames]={playerName}")
    Call<CollectionPlayersResponse> getCollectionPlayersByNames(@Path("playerName") String playerName);

    @GET("players?filter[playerIds]=account.cccdc2ed3d5746919fe29bd771d8053f")
    Call<CollectionPlayersResponse> getCollectionPlayersByIds();

    @GET("players/{id}/seasons/{seasonId}")
    Call<PlayerSeasonResponse> getPlayerSeason(@Path("id") String id, @Path("seasonId") String seasonId);

    @GET("seasons/")
    Call<SeasonResponse> getSeasons();

}