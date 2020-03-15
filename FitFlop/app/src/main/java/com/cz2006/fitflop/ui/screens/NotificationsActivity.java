package com.cz2006.fitflop.ui.screens;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.cz2006.fitflop.R;

import static com.cz2006.fitflop.R.layout.activity_notifications;

public class NotificationsActivity extends AppCompatActivity{

    LinearLayout dynamicContent, bottomNavBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);

        dynamicContent = (LinearLayout) findViewById(R.id.dynamicContent);
        bottomNavBar = (LinearLayout) findViewById(R.id.bottomNavBar);
        View wizard = getLayoutInflater().inflate(activity_notifications, null);
        dynamicContent.addView(wizard);

        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
        RadioButton rb = (RadioButton) findViewById(R.id.Notifications);

        rb.setCompoundDrawablesWithIntrinsicBounds( 0,R.drawable.ic_message_black_selected_24dp, 0,0);
        rb.setTextColor(Color.parseColor("#3F51B5"));
    }
}
