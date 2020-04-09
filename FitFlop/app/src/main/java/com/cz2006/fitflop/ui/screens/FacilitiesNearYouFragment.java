package com.cz2006.fitflop.ui.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cz2006.fitflop.R;
import com.cz2006.fitflop.UserClient;
import com.cz2006.fitflop.adapter.FacilitiesNearYouAdapter;
import com.cz2006.fitflop.ui.GeoJsonFeatureInfoActivity;

import java.util.ArrayList;

import static com.cz2006.fitflop.R.layout.activity_starred;

/**
 * This class displays the facilities near the user, sorted in ascending order by distance
 */
public class FacilitiesNearYouFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private FacilitiesNearYouAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> facilitiesNearYou = new ArrayList<>();
    private Button insert, remove; //FIXME: can delete later

    /**
     * Initialise views when fragment is first created
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(activity_starred, container, false);

        createList();

        if(facilitiesNearYou==null){
            facilitiesNearYou = new ArrayList<String>();
        }


        buildRecyclerView(view);

        return view;
    }

    /**
     * Initialise the ArrayList of facilities near the user (by retrieving the information from the UserClient class)
     */
    public void createList() {
        facilitiesNearYou = ((UserClient)(getActivity().getApplicationContext())).getFacilitiesNearYou();
    }

    /**
     * Build the recycler view (for the scrollable cardView layout)
     * @param view
     */
    private void buildRecyclerView(View view){
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new FacilitiesNearYouAdapter(facilitiesNearYou);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        /**
         * Enable clicking of each cardView item to open the information page of that fitness facility
         */
        mAdapter.setOnItemClickListener(new FacilitiesNearYouAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String name = facilitiesNearYou.get(position);
                openInformationPage(name);
            }
        });
    }

    /**
     * Method to open the information page activity
     * @param name
     */
    public void openInformationPage(String name){
        Intent intent = new Intent(getActivity(), GeoJsonFeatureInfoActivity.class);
        intent.putExtra("NAME", name);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * On resume of the fragment
     */
    @Override
    public void onResume() {
        super.onResume();
        createList();
        buildRecyclerView(getView());
    }

    /**
     * On pause of the fragment
     */
    @Override
    public void onPause() {
        super.onPause();
    }
}
