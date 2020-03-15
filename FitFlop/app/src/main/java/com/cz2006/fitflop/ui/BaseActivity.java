package com.cz2006.fitflop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.cz2006.fitflop.R;
import com.cz2006.fitflop.ui.screens.MapsActivity;
import com.cz2006.fitflop.ui.screens.NotificationsActivity;
import com.cz2006.fitflop.ui.screens.ProfileActivity;
import com.cz2006.fitflop.ui.screens.SettingsActivity;
import com.cz2006.fitflop.ui.screens.StarredActivity;

public class BaseActivity extends AppCompatActivity {


    RadioGroup radioGroup1;
    RadioButton screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);


        radioGroup1=(RadioGroup)findViewById(R.id.radioGroup1);
        screen = (RadioButton)findViewById(R.id.screen);
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                Intent in;
                Log.i("matching", "matching inside1 bro" + checkedId);
                switch (checkedId)
                {
                    case R.id.Starred:
                        Log.i("matching", "matching inside1 matching" +  checkedId);
                        in=new Intent(getBaseContext(), StarredActivity.class);
                        startActivity(in);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.Notifications:
                        Log.i("matching", "matching inside1 watchlistAdapter" + checkedId);

                        in = new Intent(getBaseContext(), NotificationsActivity.class);
                        startActivity(in);
                        overridePendingTransition(0, 0);

                        break;
                    case R.id.Home:
                        Log.i("matching", "matching inside1 rate" + checkedId);

                        in = new Intent(getBaseContext(), MapsActivity.class);
                        startActivity(in);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.Profile:
                        Log.i("matching", "matching inside1 listing" + checkedId);
                        in = new Intent(getBaseContext(), ProfileActivity.class);
                        startActivity(in);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.Settings:
                        Log.i("matching", "matching inside1 deals" + checkedId);
                        in = new Intent(getBaseContext(), SettingsActivity.class);
                        startActivity(in);
                        overridePendingTransition(0, 0);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}