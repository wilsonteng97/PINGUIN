package com.cz2006.fitflop.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cz2006.fitflop.R;

/**
 * View to contain and interchange between the Login and Register fragments
 */
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
        LoginFragment fragment = new LoginFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_login_reg,fragment);
        fragmentTransaction.commit();
    }
}
