package com.shareefoo.pubgcompanion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.shareefoo.pubgcompanion.R;
import com.shareefoo.pubgcompanion.api.ApiClient;
import com.shareefoo.pubgcompanion.api.ApiInterface;
import com.shareefoo.pubgcompanion.data.SpManager;
import com.shareefoo.pubgcompanion.dialogs.UsernameDialogActivity;
import com.shareefoo.pubgcompanion.fragments.MapsFragment;
import com.shareefoo.pubgcompanion.fragments.MatchesFragment;
import com.shareefoo.pubgcompanion.fragments.OverviewFragment;
import com.shareefoo.pubgcompanion.model.CollectionPlayersResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private final int SEARCH_DIALOG_REQUEST_CODE = 100;

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();
        toolbar.setTitle("Home");

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
                    //
                    toolbar.setTitle("Overview");
                    fragment = new OverviewFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_matches:
                    //
                    toolbar.setTitle("Matches");
                    fragment = new MatchesFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_maps:
                    //
                    toolbar.setTitle("Maps");
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

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSearchDialog() {
        Intent intent = new Intent(this, UsernameDialogActivity.class);
        startActivityForResult(intent, SEARCH_DIALOG_REQUEST_CODE);
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

                        SpManager spManager = SpManager.getInstance(MainActivity.this);
                        spManager.putBoolean("player_fetched", true);
                        spManager.putString("player_id", playerId);
                        spManager.putString("player_name", playerName);

                        OverviewFragment overviewFragment = OverviewFragment.newInstance(playerId, playerName);
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
    }

}
