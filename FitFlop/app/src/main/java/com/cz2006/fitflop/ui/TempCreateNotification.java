package com.cz2006.fitflop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.cz2006.fitflop.R;
import com.cz2006.fitflop.adapter.CreateNotificationsAdapter;

public class TempCreateNotification extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TempCreateNotification";

    // widgets
    private EditText mTitle, mMessage, mClassLink;
    private Button mSend, mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_create_notification);

        mTitle = findViewById(R.id.titleNotification);
        mMessage = findViewById(R.id.messageNotification);
        mClassLink = findViewById(R.id.classLink);

        mSend = findViewById(R.id.sendNotification);
        mBack = findViewById(R.id.backButton);

        mSend.setOnClickListener(this);
        mBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.sendNotification:{
                String title = mTitle.getText().toString();
                String message = mMessage.getText().toString();
                String link = mClassLink.getText().toString();


                CreateNotificationsAdapter NotificationsAdapter = new CreateNotificationsAdapter();
                NotificationsAdapter.createNewNotifications(title, message, link);

            }
            case R.id.backButton:{
                Intent intent = new Intent(this, BaseActivity.class);
                intent.putExtra("Notification_Fragment", 2);
                startActivity(intent);
            }
        }
    }

}
