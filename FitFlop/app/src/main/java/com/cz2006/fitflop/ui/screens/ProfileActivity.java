package com.cz2006.fitflop.ui.screens;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cz2006.fitflop.R;
import com.cz2006.fitflop.UserClient;
import com.cz2006.fitflop.model.User;
import com.cz2006.fitflop.ui.LoginView;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.Objects;

import static com.cz2006.fitflop.R.layout.activity_profiles;

public class ProfileActivity extends Fragment implements View.OnClickListener{
    private static final String TAG = "Profile Activity";

    private String name, email, BMI_string;
    private TextView nameView, emailView, BMIView;
    private double height, weight, BMI;
    private Button logoutButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(activity_profiles, container, false);

        // FIXME -> For testing purposes only! (Need to get user data from the current user)
        User user = ((UserClient)(getActivity().getApplicationContext())).getUser();
        if (user==null) {
            user = new User("TestEmail@mail.com", "3mTjQ1eGZEfLHrqNqka2cLk3Qui2", "TestEmail", "test_avatar");
        }
        user = new User("TestEmail@mail.com", "3mTjQ1eGZEfLHrqNqka2cLk3Qui2", "TestEmail", "test_avatar");
        //Log.d(TAG, "insertNewMessage: retrieved user client: " + user.toString());

        // Get User Info
        name = user.getUsername();
        email = user.getEmail();

        // Initialise Views
        nameView = view.findViewById(R.id.userName);
        emailView = view.findViewById(R.id.emailAddress);
        BMIView = view.findViewById(R.id.calculated_BMI);

        // Initialise Button
        logoutButton = (Button) view.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(this);

        // Set Data
        nameView.setText(name);
        emailView.setText(email);

        // FIXME --> temporary height and weight to calculate BMI, need to use user height and weight
        height = 1.75;
        weight = 60;
        BMI = weight / (height * height);

        BMIView.setText(Double.toString(BMI));

        return view;
    }

    private void signOut(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(), LoginView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout_button:
                signOut();
                break;
        }
    }


//    LinearLayout dynamicContent, bottomNavBar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.base_activity);
//
//        dynamicContent = (LinearLayout) findViewById(R.id.dynamicContent);
//        bottomNavBar = (LinearLayout) findViewById(R.id.bottomNavBar);
//        View wizard = getLayoutInflater().inflate(activity_profiles, null);
//        dynamicContent.addView(wizard);
//
//        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
//        RadioButton rb = (RadioButton) findViewById(R.id.Profile);
//
//        rb.setCompoundDrawablesWithIntrinsicBounds( 0,R.drawable.ic_profile_black_selected_24dp, 0,0);
//        rb.setTextColor(Color.parseColor("#3F51B5"));
//    }
}
