package com.cz2006.fitflop.ui.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cz2006.fitflop.R;
import com.cz2006.fitflop.model.Notification;
import com.cz2006.fitflop.ui.TempCreateNotification;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;

import static com.cz2006.fitflop.R.layout.activity_notifications;

public class NotificationsFragment extends Fragment implements View.OnClickListener{

    private RecyclerView notificationsRv;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;


    public NotificationsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(activity_notifications, container, false);

        notificationsRv = view.findViewById(R.id.notificationsRv);
        view.findViewById(R.id.postNotification).setOnClickListener(this);

        firebaseFirestore = FirebaseFirestore.getInstance();
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Query query = firebaseFirestore.collection("Notifications").whereEqualTo("user", user);
        FirestoreRecyclerOptions<Notification> notifications = new FirestoreRecyclerOptions.Builder<Notification>()
                .setQuery(query, Notification.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Notification, NotificationViewHolder>(notifications) {
            @NonNull
            @Override
            public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification, parent, false);
                return new NotificationViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull NotificationViewHolder holder, int position, @NonNull Notification model) {
                holder.titleTv.setText(model.getTitle());
                holder.messageTv.setText(model.getMessage());

                SimpleDateFormat spf = new SimpleDateFormat("MMM dd, yyyy");
                String date = spf.format(model.getTimestamp());
                holder.timeTv.setText(date);
            }
        };

        notificationsRv.setHasFixedSize(true);
        notificationsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationsRv.setAdapter(adapter);
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

    private class NotificationViewHolder extends RecyclerView.ViewHolder {

        private ImageView iconIv;
        private TextView titleTv, messageTv, timeTv;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            iconIv = itemView.findViewById(R.id.iconIv);
            titleTv = itemView.findViewById(R.id.titleTv);
            timeTv = itemView.findViewById(R.id.timeTv);
            messageTv = itemView.findViewById(R.id.messageTv);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();;
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