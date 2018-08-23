package com.shareefoo.pubgcompanion.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shareefoo.pubgcompanion.R;
import com.shareefoo.pubgcompanion.api.ApiClient;
import com.shareefoo.pubgcompanion.api.ApiInterface;
import com.shareefoo.pubgcompanion.data.SpManager;
import com.shareefoo.pubgcompanion.model.Duo;
import com.shareefoo.pubgcompanion.model.DuoFpp;
import com.shareefoo.pubgcompanion.model.PlayerSeasonResponse;
import com.shareefoo.pubgcompanion.model.SeasonData;
import com.shareefoo.pubgcompanion.model.SeasonResponse;
import com.shareefoo.pubgcompanion.model.Solo;
import com.shareefoo.pubgcompanion.model.SoloFpp;
import com.shareefoo.pubgcompanion.model.Squad;
import com.shareefoo.pubgcompanion.model.SquadFpp;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OverviewFragment extends Fragment {

    public static final String TAG = OverviewFragment.class.getSimpleName();

    @BindView(R.id.textView_playerName)
    TextView textViewPlayerName;

    @BindView(R.id.spinner_seasons)
    Spinner spinnerSeasons;

    @BindView(R.id.spinner_modes)
    Spinner spinnerModes;

    @BindView(R.id.textView_games)
    TextView textViewGames;

    @BindView(R.id.textView_wins)
    TextView textViewWins;

    @BindView(R.id.textView_top10)
    TextView textViewTop10;

    @BindView(R.id.textView_kd)
    TextView textViewKD;

    @BindView(R.id.textView_avg_dmg)
    TextView textViewAvgDmg;

    private static final String ARG_PLAYER_ID = "player_id";
    private static final String ARG_PLAYER_NAME = "player_name";

    private String mPlayerId;
    private String mPlayerName;

    private SpManager spManager;

    public OverviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param playerId   Id of the player.
     * @param playerName name of the player.
     * @return A new instance of fragment OverviewFragment.
     */
    public static OverviewFragment newInstance(String playerId, String playerName) {
        OverviewFragment fragment = new OverviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PLAYER_ID, playerId);
        args.putString(ARG_PLAYER_NAME, playerName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        spManager = SpManager.getInstance(getContext());

        if (getArguments() != null) {
            mPlayerId = getArguments().getString(ARG_PLAYER_ID);
            mPlayerName = getArguments().getString(ARG_PLAYER_NAME);

        } else if (spManager.getBoolean("player_fetched", false)) {
            mPlayerId = spManager.getString("player_id", "");
            mPlayerName = spManager.getString("player_name", "");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_overview, container, false);
        ButterKnife.bind(this, rootView);

        // Check if player name selected
        if (!TextUtils.isEmpty(mPlayerName)) {

            textViewPlayerName.setText(mPlayerName);

            spinnerModes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    String mode = adapterView.getSelectedItem().toString();

//                    String mode;
//
//                    if (!spManager.getString("game_mode", "").isEmpty()) {
//                        mode = spManager.getString("game_mode", "");
//                    } else {
//                        mode = adapterView.getSelectedItem().toString();
//                    }
//
//                    spManager.putString("game_mode", mode);

                    getSeasons(mode);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } else {
            //

        }

        return rootView;
    }

    private void getSeasons(final String mode) {
        //
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SeasonResponse> seasonResponseCall = apiService.getSeasons();
        seasonResponseCall.enqueue(new Callback<SeasonResponse>() {
            @Override
            public void onResponse(@NonNull Call<SeasonResponse> call, @NonNull Response<SeasonResponse> response) {
                Log.d(TAG, "onResponse: " + response.toString());

                if (response.isSuccessful()) {

                    SeasonResponse seasonResponse = response.body();

                    if (seasonResponse != null) {

                        List<String> seasonsNames = new ArrayList<>();

                        final List<SeasonData> seasonDataList = seasonResponse.getSeasonData();
                        for (SeasonData seasonData : seasonDataList) {
                            String id = seasonData.getId();
                            int startIndex = id.lastIndexOf(".");
                            String seasonName = id.substring(startIndex + 1, id.length());
                            seasonsNames.add(seasonName);
                        }

                        Collections.reverse(seasonsNames);

                        Collections.reverse(seasonDataList);

                        ArrayAdapter<String> seasonsAdapter = new ArrayAdapter<String>(getContext(),
                                R.layout.simple_spinner_item, seasonsNames);
                        seasonsAdapter.setDropDownViewResource(R.layout.simple_spinner_item);

                        spinnerSeasons.setAdapter(seasonsAdapter);
                        spinnerSeasons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                int selectedIndex = adapterView.getSelectedItemPosition();

                                String seasonsId = null;

                                for (int j = 0; j < seasonDataList.size(); j++) {
                                    if (selectedIndex == j) {
                                        seasonsId = seasonDataList.get(j).getId();
                                    }
                                }

                                getPlayerSeason(mPlayerId, seasonsId, mode);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                    }

                } else {
                    try {
                        JSONObject errorObject = new JSONObject(response.errorBody().string());
                        Log.e(TAG, "onResponse: " + errorObject.toString());
                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SeasonResponse> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void getPlayerSeason(String playerId, String seasonId, final String mode) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<PlayerSeasonResponse> playerSeasonResponseCall = apiService.getPlayerSeason(playerId, seasonId);
        playerSeasonResponseCall.enqueue(new Callback<PlayerSeasonResponse>() {
            @Override
            public void onResponse(@NonNull Call<PlayerSeasonResponse> call, @NonNull Response<PlayerSeasonResponse> response) {
                Log.d(TAG, "onResponse: " + response.toString());

                if (response.isSuccessful()) {

                    PlayerSeasonResponse playerSeasonResponse = response.body();

                    if (playerSeasonResponse != null) {

                        if (mode.equals("Solo")) {
                            Solo solo = playerSeasonResponse.getPlayerSeasonData().getAttributes().getGameModeStats().getSolo();

                        } else if (mode.equals("Solo FPP")) {
                            SoloFpp soloFpp = playerSeasonResponse.getPlayerSeasonData().getAttributes().getGameModeStats().getSoloFpp();

                            textViewGames.setText(String.valueOf(soloFpp.getWins() + soloFpp.getLosses()));
                            textViewWins.setText(String.valueOf(soloFpp.getWins()));
                            textViewTop10.setText(String.valueOf(soloFpp.getTop10s()));
                            textViewKD.setText(String.valueOf(soloFpp.getKills() / soloFpp.getLosses()));
                            textViewAvgDmg.setText(String.valueOf(soloFpp.getDamageDealt()));

                        } else if (mode.equals("Duo")) {
                            Duo duo = playerSeasonResponse.getPlayerSeasonData().getAttributes().getGameModeStats().getDuo();

                        } else if (mode.equals("Duo FPP")) {
                            DuoFpp duoFpp = playerSeasonResponse.getPlayerSeasonData().getAttributes().getGameModeStats().getDuoFpp();

                        } else if (mode.equals("Squad")) {
                            Squad squad = playerSeasonResponse.getPlayerSeasonData().getAttributes().getGameModeStats().getSquad();

                        } else if (mode.equals("Squad FPP")) {
                            SquadFpp squadFpp = playerSeasonResponse.getPlayerSeasonData().getAttributes().getGameModeStats().getSquadFpp();

                        }

                    }

                } else {
                    try {
                        JSONObject errorObject = new JSONObject(response.errorBody().string());
                        Log.e(TAG, "onResponse: " + errorObject.toString());
                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PlayerSeasonResponse> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}
