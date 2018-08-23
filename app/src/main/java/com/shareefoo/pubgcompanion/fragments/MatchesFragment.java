package com.shareefoo.pubgcompanion.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shareefoo.pubgcompanion.R;
import com.shareefoo.pubgcompanion.adapters.MatchAdapter;
import com.shareefoo.pubgcompanion.model.match.AttributesStats;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MatchesFragment extends Fragment {

    @BindView(R.id.recyclerView_matches)
    RecyclerView recyclerViewMatches;

    public MatchesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_matches, container, false);
        ButterKnife.bind(this, rootView);

        List<AttributesStats> stats = new ArrayList<>();

        AttributesStats stats1 = new AttributesStats();
        stats1.setRank(1);
        stats1.setKills(13);
        stats1.setDamageDealt(675.000031);
        stats1.setWalkDistance(2797.09448);

        AttributesStats stats2 = new AttributesStats();
        stats2.setRank(9);
        stats2.setKills(9);
        stats2.setDamageDealt(675.000031);
        stats2.setWalkDistance(2797.09448);

        AttributesStats stats3 = new AttributesStats();
        stats3.setRank(22);
        stats3.setKills(6);
        stats3.setDamageDealt(675.000031);
        stats3.setWalkDistance(2797.09448);

        stats.add(stats1);
        stats.add(stats2);
        stats.add(stats3);

        MatchAdapter adapter = new MatchAdapter(getContext(), stats);

        recyclerViewMatches.setAdapter(adapter);
        recyclerViewMatches.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMatches.setHasFixedSize(true);

        return rootView;
    }

}
