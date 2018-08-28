package com.shareefoo.pubgcompanion.fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shareefoo.pubgcompanion.R;
import com.shareefoo.pubgcompanion.adapters.MatchAdapter;
import com.shareefoo.pubgcompanion.api.ApiClient;
import com.shareefoo.pubgcompanion.api.ApiInterface;
import com.shareefoo.pubgcompanion.data.SpManager;
import com.shareefoo.pubgcompanion.model.PlayerSeasonMatchesData;
import com.shareefoo.pubgcompanion.model.match.AttributesStats;
import com.shareefoo.pubgcompanion.model.match.MatchIncluded;
import com.shareefoo.pubgcompanion.model.match.MatchResponse;
import com.shareefoo.pubgcompanion.provider.MatchContract;
import com.shareefoo.pubgcompanion.utils.NetworkUtils;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shareefoo.pubgcompanion.provider.MatchContract.BASE_CONTENT_URI;
import static com.shareefoo.pubgcompanion.provider.MatchContract.PATH_MATCHES;

public class MatchesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = MatchesFragment.class.getSimpleName();

    @BindView(R.id.recyclerView_matches)
    RecyclerView recyclerViewMatches;

    @BindView(R.id.textView_empty)
    TextView textViewEmpty;

//    @BindView(R.id.progressBar)
//    ProgressBar progressBar;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout refreshLayout;

    private MatchAdapter mAdapter;

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

        mAdapter = new MatchAdapter(getContext(), null);

        recyclerViewMatches.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMatches.setHasFixedSize(true);

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadMatchesInfo();
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMatchesInfo();
            }
        });

        refreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Prepare the loader.  Either re-connect with an existing one or start a new one.
        getLoaderManager().initLoader(0, null, this);
    }

    private void loadMatchesInfo() {

        if (NetworkUtils.IsNetworkAvailable(getContext())) {

            getActivity().getContentResolver().delete(MatchContract.MatchEntry.CONTENT_URI, null, null);

            final String playerId = spManager.getString("player_id", "");

            if (matchesData != null && matchesData.size() > 0) {

                refreshLayout.setRefreshing(true);

//                progressBar.setVisibility(View.VISIBLE);

//            recyclerViewMatches.setVisibility(View.VISIBLE);
//            textViewEmpty.setVisibility(View.GONE);

                for (int i = 0; i < matchesData.size(); i++) {

                    //
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    Call<MatchResponse> matchResponseCall = apiService.getMatchById(matchesData.get(i).getId());
                    matchResponseCall.enqueue(new Callback<MatchResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<MatchResponse> call, @NonNull Response<MatchResponse> response) {
                            Log.d(TAG, "onResponse: " + response.toString());
//                            progressBar.setVisibility(View.INVISIBLE);

                            if (response.isSuccessful()) {

                                MatchResponse matchResponse = response.body();

                                if (matchResponse != null) {

                                    List<MatchIncluded> included = matchResponse.getIncluded();

                                    for (int j = 0; j < included.size(); j++) {

                                        if (included.get(j).getAttributes().getStats() != null) {

                                            if (included.get(j).getAttributes().getStats().getPlayerId() != null) {

                                                if (playerId.equals(included.get(j).getAttributes().getStats().getPlayerId())) {

                                                    int placement = included.get(j).getAttributes().getStats().getWinPlace();
                                                    int kills = included.get(j).getAttributes().getStats().getKills();
                                                    double damage = included.get(j).getAttributes().getStats().getDamageDealt();
                                                    double distance = included.get(j).getAttributes().getStats().getWalkDistance();

                                                    AttributesStats stats = new AttributesStats();
                                                    stats.setRank(placement);
                                                    stats.setKills(kills);
                                                    stats.setDamageDealt(damage);
                                                    stats.setWalkDistance(distance);

                                                    statsList.add(stats);

                                                    new InsertDataTask().execute(String.valueOf(placement),
                                                            String.valueOf(kills),
                                                            String.valueOf(damage),
                                                            String.valueOf(distance));
                                                }

                                            }

                                        }

                                    }

                                    recyclerViewMatches.setAdapter(mAdapter);

                                    refreshLayout.setRefreshing(false);
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
//                            progressBar.setVisibility(View.INVISIBLE);
                            refreshLayout.setRefreshing(true);
                        }
                    });

                }

            } else {
                recyclerViewMatches.setVisibility(View.GONE);
                textViewEmpty.setVisibility(View.VISIBLE);
            }

        } else {
            Toast.makeText(getContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
        }

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Uri MATCH_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MATCHES).build();
        return new CursorLoader(getContext(), MATCH_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

//    public void setMatchesData(List<PlayerSeasonMatchesData> matchesData) {
//        this.matchesData = matchesData;
//    }

    private class InsertDataTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(String... strings) {

            int placement = Integer.parseInt(strings[0]);
            int kills = Integer.parseInt(strings[1]);
            double damage = Double.parseDouble(strings[2]);
            double distance = Double.parseDouble(strings[3]);

            // Insert new match into DB
            ContentValues contentValues = new ContentValues();
            contentValues.put(MatchContract.MatchEntry.COLUMN_PLACEMENT, placement);
            contentValues.put(MatchContract.MatchEntry.COLUMN_KILLS, kills);
            contentValues.put(MatchContract.MatchEntry.COLUMN_DAMAGE, damage);
            contentValues.put(MatchContract.MatchEntry.COLUMN_DISTANCE, distance);
            getActivity().getContentResolver().insert(MatchContract.MatchEntry.CONTENT_URI, contentValues);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d(TAG, "onPostExecute: Data inserted successfully..");
        }

    }

}
