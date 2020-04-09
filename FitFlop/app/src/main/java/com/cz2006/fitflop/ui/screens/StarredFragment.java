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
import com.cz2006.fitflop.controller.StarredAdapter;
import com.cz2006.fitflop.model.StarredItem;
import com.cz2006.fitflop.model.User;
import com.cz2006.fitflop.ui.GeoJsonFeatureInfoActivity;

import java.util.ArrayList;

import static com.cz2006.fitflop.R.layout.activity_starred;

/**
 * Starred page to display the fitness facilities starred by the user
 */
public class StarredFragment extends Fragment {

    private RecyclerView mRecyclerView;
    //private RecyclerView.Adapter mAdapter;
    private StarredAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<StarredItem> starredItems = new ArrayList<>();
    private Button insert, remove; //FIXME: can delete later
    private User user;

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

        user = ((UserClient)(getActivity().getApplicationContext())).getUser();
        if (user==null) {
            user = new User("TestEmail@mail.com", "3mTjQ1eGZEfLHrqNqka2cLk3Qui2", "TestEmail", "test_avatar", 1.75f, 65);
        }
        if(user.getStarredItems()==null){
            starredItems = new ArrayList<StarredItem>();
            user.setStarredItems(starredItems);
        }

        createList();
        buildRecyclerView(view);

        return view;
    }

    /**
     * Initialise the ArrayList of starred items (retrieve the list from the user database)
     */
    public void createList(){
        starredItems = user.getStarredItems();
    }

    /**
     * Build the recycler view to contain the scrollable cardViews which display the starred locations
     * @param view
     */
    private void buildRecyclerView(View view){
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new StarredAdapter(starredItems);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        /**
         * Enable clicking of the cardView to open the information page for that fitness facility
         */
        mAdapter.setOnItemClickListener(new StarredAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String name = starredItems.get(position).getName();
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

        //update Views
    }

    /**
     * On pause of the fragment
     */
    @Override
    public void onPause() {
        super.onPause();
    }

}
