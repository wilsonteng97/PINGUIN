package com.cz2006.fitflop.ui;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cz2006.fitflop.UserClient;
import com.cz2006.fitflop.model.Notification;
import com.cz2006.fitflop.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class NotificationsAdapter {

    private static final String TAG = "NotificationsAdapter";

    String username;

    public void createNewNotifications(String title, String message, String link){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference newNotificationsRef = db
                .collection("Notifications")
                .document();

        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setClass_link(link);
        notification.setUser(userId);
        notification.setHasRead(false);

        newNotificationsRef.set(notification).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                }else{

                }
            }
        });

    }
}
