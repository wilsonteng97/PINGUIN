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
import com.cz2006.fitflop.adapter.StarredAdapter;
import com.cz2006.fitflop.model.StarredItem;
import com.cz2006.fitflop.model.User;
import com.cz2006.fitflop.ui.GeoJsonFeatureInfoActivity;

import java.util.ArrayList;

import static com.cz2006.fitflop.R.layout.activity_starred;

public class FacilitiesNearYouFragment extends Fragment {
    private RecyclerView mRecyclerView;
    //private RecyclerView.Adapter mAdapter;
    private FacilitiesNearYouAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> facilitiesNearYou = new ArrayList<>();
    private Button insert, remove; //FIXME: can delete later

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(activity_starred, container, false);

        createList();

        if(facilitiesNearYou==null){
            facilitiesNearYou = new ArrayList<String>();
//            ((UserClient)getActivity().getApplicationContext()).setFacilitiesNearYou(facilitiesNearYou);
        }


        buildRecyclerView(view);

        return view;
    }

    public void createList() {
        facilitiesNearYou = ((UserClient)(getActivity().getApplicationContext())).getFacilitiesNearYou();
    }

    private void buildRecyclerView(View view){
        mRecyclerView = view.findViewById(R.id.recycler_view);
        //mRecyclerView.setHasFixedSize(true); //if you know that recycler view in xml layout will not change in size no matter how many items are inside
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new FacilitiesNearYouAdapter(facilitiesNearYou);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter.setOnItemClickListener(new FacilitiesNearYouAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String name = facilitiesNearYou.get(position);
                openInformationPage(name);
                //starredItems.get(position);
            }
        });
    }

    public void openInformationPage(String name){
        Intent intent = new Intent(getActivity(), GeoJsonFeatureInfoActivity.class);
        intent.putExtra("NAME", name);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        createList();
        buildRecyclerView(getView());
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
