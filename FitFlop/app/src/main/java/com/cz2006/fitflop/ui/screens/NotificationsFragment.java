package com.cz2006.fitflop.ui.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cz2006.fitflop.R;
import com.cz2006.fitflop.adapter.NotificationsRecyclerViewAdapter;
import com.cz2006.fitflop.model.Notification;
import com.cz2006.fitflop.ui.TempCreateNotification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.cz2006.fitflop.R.layout.activity_notifications;

public class NotificationsFragment extends Fragment implements View.OnClickListener{

    private RecyclerView notificationsRv;
    private NotificationsRecyclerViewAdapter adapter;
    private ArrayList<Notification> list;
    private FirebaseFirestore db;
    private RecyclerView.LayoutManager layoutManager;


    public NotificationsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(activity_notifications, container, false);

        view.findViewById(R.id.postNotification).setOnClickListener(this);
        notificationsRv = (RecyclerView) view.findViewById(R.id.notificationsRv);
        db = FirebaseFirestore.getInstance();
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("Notifications").whereEqualTo("user", user).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                list = new ArrayList<Notification>();
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot querySnapshot : task.getResult()){
                        Notification n = new Notification(querySnapshot.getString("user"), querySnapshot.getString("title"), querySnapshot.getString("message"),
                                querySnapshot.getDate("timestamp"), querySnapshot.getString("class_link"), querySnapshot.getBoolean("hasRead"));

                        list.add(n);
                        adapter = new NotificationsRecyclerViewAdapter(getActivity().getApplicationContext(), list);
                        notificationsRv.setAdapter(adapter);
                    }
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "FAILED", Toast.LENGTH_SHORT).show();
                }
            }
        });


        notificationsRv.setLayoutManager(new LinearLayoutManager(getContext()));

        notificationsRv.setHasFixedSize(true);
        notificationsRv.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.postNotification: {
                Intent intent = new Intent(getActivity(), TempCreateNotification.class);
                startActivity(intent);
            }
        }
    }

//    LinearLayout dynamicContent, bottomNavBar;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.base_activity);
//
//        dynamicContent = (LinearLayout) findViewById(R.id.dynamicContent);
//        bottomNavBar = (LinearLayout) findViewById(R.id.bottomNavBar);
//        View wizard = getLayoutInflater().inflate(activity_notifications, null);
//        dynamicContent.addView(wizard);
//
//        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
//        RadioButton rb = (RadioButton) findViewById(R.id.Notifications);
//
//        rb.setCompoundDrawablesWithIntrinsicBounds( 0,R.drawable.ic_message_black_selected_24dp, 0,0);
//        rb.setTextColor(Color.parseColor("#3F51B5"));
//    }
}