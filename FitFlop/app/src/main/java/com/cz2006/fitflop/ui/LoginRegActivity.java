package com.cz2006.fitflop.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;
import com.cz2006.fitflop.R;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginRegActivity extends AppCompatActivity {

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_reg);

        addFragment();

    }

    public void addFragment(){
        LoginView fragment = new LoginView();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_login_reg,fragment);
        fragmentTransaction.commit();
    }
}
