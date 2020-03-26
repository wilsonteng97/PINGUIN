package com.cz2006.fitflop.ui.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cz2006.fitflop.R;
import com.cz2006.fitflop.adapter.SettingsAdapter;

import static com.cz2006.fitflop.R.layout.activity_settings;

public class SettingsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(activity_settings, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view_settings);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SettingsAdapter();
        mRecyclerView.setAdapter(mAdapter);

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
//        View wizard = getLayoutInflater().inflate(activity_settings, null);
//        dynamicContent.addView(wizard);
//
//        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
//        RadioButton rb = (RadioButton) findViewById(R.id.Settings);
//
//        rb.setCompoundDrawablesWithIntrinsicBounds( 0,R.drawable.ic_settings_black_selected_24dp, 0,0);
//        rb.setTextColor(Color.parseColor("#3F51B5"));
//    }
}

