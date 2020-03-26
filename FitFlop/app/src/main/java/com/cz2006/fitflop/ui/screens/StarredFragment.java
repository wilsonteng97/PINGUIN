package com.cz2006.fitflop.ui.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cz2006.fitflop.R;
import com.cz2006.fitflop.adapter.StarredAdapter;
import com.cz2006.fitflop.model.StarredItem;

import java.util.ArrayList;

import static com.cz2006.fitflop.R.layout.activity_starred;

public class StarredFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(activity_starred, container, false);

        ArrayList<StarredItem> starredItems = new ArrayList<>();
        starredItems.add(new StarredItem("Name1", "Address1"));
        starredItems.add(new StarredItem("Name2", "Address2"));
        starredItems.add(new StarredItem("Name3", "Address3"));
        starredItems.add(new StarredItem("Name4", "Address4"));
        starredItems.add(new StarredItem("Name5", "Address5"));
        starredItems.add(new StarredItem("Name6", "Address6"));
        starredItems.add(new StarredItem("Name7", "Address7"));
        starredItems.add(new StarredItem("Name8", "Address8"));
        starredItems.add(new StarredItem("Name9", "Address9"));
        starredItems.add(new StarredItem("Name10", "Address10"));

        mRecyclerView = view.findViewById(R.id.recycler_view);
        //mRecyclerView.setHasFixedSize(true); //if you know that recycler view in xml layout will not change in size no matter how many items are inside
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new StarredAdapter(starredItems);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }







//    LinearLayout dynamicContent, bottomNavBar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.base_activity);
//
//        dynamicContent = (LinearLayout) findViewById(R.id.dynamicContent);
//        bottomNavBar = (LinearLayout) findViewById(R.id.bottomNavBar);
//        View wizard = getLayoutInflater().inflate(activity_starred, null);
//        dynamicContent.addView(wizard);
//
//        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
//        RadioButton rb = (RadioButton) findViewById(R.id.Starred);
//
//        rb.setCompoundDrawablesWithIntrinsicBounds( 0,R.drawable.ic_star_black_selected_24dp, 0,0);
//        rb.setTextColor(Color.parseColor("#3F51B5"));
//    }
}
