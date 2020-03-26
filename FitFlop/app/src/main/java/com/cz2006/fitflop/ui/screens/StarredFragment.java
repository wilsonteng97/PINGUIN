package com.cz2006.fitflop.ui.screens;

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
import com.cz2006.fitflop.adapter.StarredAdapter;
import com.cz2006.fitflop.model.StarredItem;
import com.cz2006.fitflop.model.User;

import java.util.ArrayList;

import static com.cz2006.fitflop.R.layout.activity_starred;

public class StarredFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<StarredItem> starredItems = new ArrayList<>();
    private Button insert, remove; //FIXME: can delete later
    private User user;

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

        /*insert = view.findViewById(R.id.insert); //TODO: Remove buttons
        remove = view.findViewById(R.id.remove);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertItem("Name4", "Address4");
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = "Name2"; //TODO: get name of gym from GeoJsonFeatureHashMapInfo method and pass in here
                removeItem(name);
            }
        });*/


        return view;
    }

    public void createList(){
        starredItems = user.getStarredItems();
        //starredItems.add(new StarredItem("Name1", "Address1"));
        //starredItems.add(new StarredItem("Name2", "Address2"));
        //starredItems.add(new StarredItem("Name3", "Address3"));
    }

    public void insertItem(String nameOfGym, String addressOfGym){
        int j;
        boolean k=false;
        if(starredItems != null) {
            for (j = 0; j < starredItems.size(); j++) {
                if (starredItems.get(j).getName() == nameOfGym) {  // If item is already inside, don't add it
                    k = true;
                }
            }
        }

        if (k==false){
            starredItems.add(new StarredItem(nameOfGym, addressOfGym));
            //mAdapter.notifyDataSetChanged();
            mAdapter.notifyItemInserted(starredItems.size());
        }
        user.setStarredItems(starredItems);
        ((UserClient) getActivity().getApplicationContext()).setUser(user);
    }

    public void removeItem(String name){
        int i;
        for (i=0;i<starredItems.size();i++){
            if (starredItems.get(i).getName() == name){
                starredItems.remove(i);
                break;
            }
        }
        mAdapter.notifyItemRemoved(i);
        user.setStarredItems(starredItems);
        ((UserClient) getActivity().getApplicationContext()).setUser(user);

    }

    private void buildRecyclerView(View view){
        mRecyclerView = view.findViewById(R.id.recycler_view);
        //mRecyclerView.setHasFixedSize(true); //if you know that recycler view in xml layout will not change in size no matter how many items are inside
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new StarredAdapter(starredItems);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }



    @Override
    public void onResume() {
        super.onResume();
        createList();
        buildRecyclerView(getView());

        //update Views
    }

    @Override
    public void onPause() {
        super.onPause();
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
