package com.shareefoo.pubgcompanion.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.shareefoo.pubgcompanion.R;
import com.shareefoo.pubgcompanion.api.ApiClient;
import com.shareefoo.pubgcompanion.api.ApiInterface;
import com.shareefoo.pubgcompanion.data.SpManager;
import com.shareefoo.pubgcompanion.dialogs.UsernameDialogActivity;
import com.shareefoo.pubgcompanion.fragments.MapsFragment;
import com.shareefoo.pubgcompanion.fragments.MatchesFragment;
import com.shareefoo.pubgcompanion.fragments.OverviewFragment;
import com.shareefoo.pubgcompanion.model.CollectionPlayersResponse;
import com.shareefoo.pubgcompanion.model.PlayerSeasonMatchesData;
import com.shareefoo.pubgcompanion.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OverviewFragment.OnMatchesDataLoad {

    public static final String TAG = MainActivity.class.getSimpleName();

    private final int SEARCH_DIALOG_REQUEST_CODE = 100;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private SpManager spManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        MobileAds.initialize(this, getResources().getString(R.string.admob_app_id));

        spManager = SpManager.getInstance(this);

        if (TextUtils.isEmpty(spManager.getString("player_name", ""))) {
            showSearchDialog();
        }

//        getSupportActionBar().setTitle("Overview");
        loadFragment(new OverviewFragment());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_overview:
//                    getSupportActionBar().setTitle("Overview");
                    fragment = new OverviewFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_matches:
//                    getSupportActionBar().setTitle("Matches");
                    fragment = new MatchesFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_maps:
//                    getSupportActionBar().setTitle("Maps");
                    fragment = new MapsFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        //
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.action_search:
                showSearchDialog();
                return true;

            case R.id.action_nearby_players:
                showNearbyPlayersActivity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSearchDialog() {
        Intent intent = new Intent(this, UsernameDialogActivity.class);
        startActivityForResult(intent, SEARCH_DIALOG_REQUEST_CODE);
    }

    private void showNearbyPlayersActivity() {
        // TODO: make this option in separate Settings Activity
        if (!spManager.getBoolean("share_location", false)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Would you like to share your location with other players ?");
            builder.setCancelable(false);
            builder.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            spManager.putBoolean("share_location", true);
                            Intent intent = new Intent(MainActivity.this, NearbyPlayersActivity.class);
                            startActivity(intent);
                        }
                    });

            builder.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert1 = builder.create();
            alert1.show();

        } else {
            Intent intent = new Intent(MainActivity.this, NearbyPlayersActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SEARCH_DIALOG_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //
                String playerName = data.getStringExtra("player_name");
                getPlayerByNameRequest(playerName);
            }
        }
    }

    private void getPlayerByNameRequest(final String playerName) {
        if (NetworkUtils.IsNetworkAvailable(this)) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<CollectionPlayersResponse> playersResponseCall = apiService.getCollectionPlayersByNames(playerName);
            playersResponseCall.enqueue(new Callback<CollectionPlayersResponse>() {
                @Override
                public void onResponse(@NonNull Call<CollectionPlayersResponse> call, @NonNull Response<CollectionPlayersResponse> response) {
                    Log.d(TAG, "onResponse: " + response.toString());

                    if (response.isSuccessful()) {

                        CollectionPlayersResponse playersResponse = response.body();

                        if (playersResponse != null) {

                            String playerId = playersResponse.getPlayerData().get(0).getId();
                            String playerName = playersResponse.getPlayerData().get(0).getAttributes().getName();

                            spManager.putBoolean("player_fetched", true);
                            spManager.putString("player_id", playerId);
                            spManager.putString("player_name", playerName);

//                        OverviewFragment overviewFragment = OverviewFragment.newInstance(playerId, playerName);
                            OverviewFragment overviewFragment = new OverviewFragment();
                            loadFragment(overviewFragment);
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
                public void onFailure(@NonNull Call<CollectionPlayersResponse> call, @NonNull Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                }
            });
        } else {
            Toast.makeText(this, R.string.no_connection, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onMatchesLoad(List<PlayerSeasonMatchesData> matchesData) {
        Log.d(TAG, "onMatchesLoad: ");
        MatchesFragment.matchesData = matchesData;
    }

}
