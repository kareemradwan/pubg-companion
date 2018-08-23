package com.shareefoo.pubgcompanion.api;

import com.shareefoo.pubgcompanion.model.CollectionPlayersResponse;
import com.shareefoo.pubgcompanion.model.PlayerSeasonResponse;
import com.shareefoo.pubgcompanion.model.SeasonResponse;
import com.shareefoo.pubgcompanion.model.SinglePlayerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by shareefoo
 */

public interface ApiInterface {

    //    @GET("matches/{id}")
//    Call<Match> getMatchById(@Path("id") String id);
//
    @GET("players/{id}")
    Call<SinglePlayerResponse> getPlayerById(@Path("id") String id);

    @GET("players")
    Call<CollectionPlayersResponse> getCollectionPlayersByNames(@Query("filter[playerNames]") String playerName);

    @GET("players")
    Call<CollectionPlayersResponse> getCollectionPlayersByIds(@Query("filter[playerIds]") String playerAccount);

    @GET("players/{id}/seasons/{seasonId}")
    Call<PlayerSeasonResponse> getPlayerSeason(@Path("id") String id, @Path("seasonId") String seasonId);

    @GET("seasons")
    Call<SeasonResponse> getSeasons();

}