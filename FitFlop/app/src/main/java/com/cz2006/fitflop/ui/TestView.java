package com.cz2006.fitflop.ui;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import androidx.appcompat.app.AppCompatActivity;

import com.cz2006.fitflop.R;
import com.google.firebase.auth.FirebaseAuth;

public class TestView extends AppCompatActivity implements
        View.OnClickListener {
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        findViewById(R.id.btn_test).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_test:{
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(TestView.this, LoginView.class);
                startActivity(intent);
                break;
            }
        }
    }
}
