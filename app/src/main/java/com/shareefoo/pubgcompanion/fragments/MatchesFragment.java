package com.shareefoo.pubgcompanion.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shareefoo.pubgcompanion.R;
import com.shareefoo.pubgcompanion.adapters.MatchAdapter;
import com.shareefoo.pubgcompanion.api.ApiClient;
import com.shareefoo.pubgcompanion.api.ApiInterface;
import com.shareefoo.pubgcompanion.data.SpManager;
import com.shareefoo.pubgcompanion.model.PlayerSeasonMatchesData;
import com.shareefoo.pubgcompanion.model.match.AttributesStats;
import com.shareefoo.pubgcompanion.model.match.MatchIncluded;
import com.shareefoo.pubgcompanion.model.match.MatchResponse;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchesFragment extends Fragment {

    public static final String TAG = MatchesFragment.class.getSimpleName();

    @BindView(R.id.recyclerView_matches)
    RecyclerView recyclerViewMatches;

    public static List<PlayerSeasonMatchesData> matchesData;

    private List<AttributesStats> statsList;

    private SpManager spManager;

    public MatchesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_matches, container, false);
        ButterKnife.bind(this, rootView);

        spManager = SpManager.getInstance(getContext());

        statsList = new ArrayList<>();

        recyclerViewMatches.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMatches.setHasFixedSize(true);

        loadMatchesInfo();

        return rootView;
    }

    private void loadMatchesInfo() {

        final String playerId = spManager.getString("player_id", "");

        for (int i = 0; i < matchesData.size(); i++) {

            //
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<MatchResponse> matchResponseCall = apiService.getMatchById(matchesData.get(i).getId());
            matchResponseCall.enqueue(new Callback<MatchResponse>() {
                @Override
                public void onResponse(@NonNull Call<MatchResponse> call, @NonNull Response<MatchResponse> response) {
                    Log.d(TAG, "onResponse: " + response.toString());

                    if (response.isSuccessful()) {

                        MatchResponse matchResponse = response.body();

                        if (matchResponse != null) {

                            List<MatchIncluded> included = matchResponse.getIncluded();

                            for (int j = 0; j < included.size(); j++) {

                                if (included.get(j).getAttributes().getStats() != null) {

                                    if (included.get(j).getAttributes().getStats().getPlayerId() != null) {

                                        if (playerId.equals(included.get(j).getAttributes().getStats().getPlayerId())) {

                                            AttributesStats stats = new AttributesStats();
                                            stats.setRank(included.get(j).getAttributes().getStats().getWinPlace());
                                            stats.setKills(included.get(j).getAttributes().getStats().getKills());
                                            stats.setDamageDealt(included.get(j).getAttributes().getStats().getDamageDealt());
                                            stats.setWalkDistance(included.get(j).getAttributes().getStats().getWalkDistance());

                                            statsList.add(stats);
                                        }

                                    }

                                }

                            }

                            MatchAdapter adapter = new MatchAdapter(getContext(), statsList);
                            recyclerViewMatches.setAdapter(adapter);

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
                public void onFailure(@NonNull Call<MatchResponse> call, @NonNull Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                }
            });

        }

    }

//    public void setMatchesData(List<PlayerSeasonMatchesData> matchesData) {
//        this.matchesData = matchesData;
//    }

}
