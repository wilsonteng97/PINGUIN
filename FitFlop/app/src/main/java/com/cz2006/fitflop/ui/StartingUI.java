package com.cz2006.fitflop.ui;

import androidx.appcompat.app.AppCompatActivity;
import com.cz2006.fitflop.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


import java.util.Timer;
import java.util.TimerTask;

public class StartingUI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_starting_ui);
        getSupportActionBar().hide();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(StartingUI.this, LoginRegView.class));
                finish();
            }
        }, 3000);

    }

}
