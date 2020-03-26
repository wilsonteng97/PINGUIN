package com.cz2006.fitflop.ui.screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cz2006.fitflop.R;
import com.cz2006.fitflop.UserClient;
import com.cz2006.fitflop.model.User;
import com.cz2006.fitflop.ui.LoginRegView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import static com.cz2006.fitflop.R.layout.activity_profiles;

public class ProfileFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "Profile Activity";

    private String name, email, BMI_string;
    private TextView nameView, emailView, BMIView, heightView, weightView;
    private EditText editHeight, editWeight;
    private float height, weight, BMI;
    private Button logoutButton;
    private Button editButton, saveButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(activity_profiles, container, false);

        // FIXME -> For testing purposes only! (Need to get user data from the current user)
        User user = ((UserClient)(getActivity().getApplicationContext())).getUser();
        if (user==null) {
            user = new User("TestEmail@mail.com", "3mTjQ1eGZEfLHrqNqka2cLk3Qui2", "TestEmail", "test_avatar", 1.75f, 65);
        }
        //Log.d(TAG, "insertNewMessage: retrieved user client: " + user.toString());

        // Retrieve User Info from Database, Initialise Variables
        name = user.getUsername();
        email = user.getEmail();
        height = user.getHeight();
        weight = user.getWeight();
        calculateBMI();

        // Initialise Views
        nameView = (TextView)view.findViewById(R.id.userName);
        emailView = (TextView)view.findViewById(R.id.emailAddress);
        BMIView = (TextView)view.findViewById(R.id.calculated_BMI);
        heightView = (TextView)view.findViewById(R.id.height_in_cm);
        weightView = (TextView)view.findViewById(R.id.weight_in_kg);
        editHeight = (EditText) view.findViewById(R.id.editHeight);
        editWeight = (EditText) view.findViewById(R.id.editWeight);

        // Set Data (Display on screen and update User info)
        nameView.setText(name);
        emailView.setText(email);
        updateViews();

        // Initialise Buttons
        logoutButton = (Button) view.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(this);
        editButton = (Button) view.findViewById(R.id.edit);
        saveButton = (Button) view.findViewById(R.id.save);
        editButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);

        // FIXME : Update height and weight into database
        user.setHeight(height);
        user.setWeight(weight);

        return view;
    }

    private void signOut(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(), LoginRegView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    private void editButtonClicked(){
        editHeight.setVisibility(View.VISIBLE);
        editWeight.setVisibility(View.VISIBLE);
        heightView.setVisibility(View.INVISIBLE);
        weightView.setVisibility(View.INVISIBLE);
        editButton.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.VISIBLE);

        // FIXME: Buggy??? When keyboard is closed the screen is not updated
        showSoftKeyboard(editHeight);
        /*if (editHeight.isFocused()){
            showSoftKeyboard(editHeight);
        }
        else if (editWeight.isFocused()){
            showSoftKeyboard(editWeight);
        }*/
    }

    private void saveButtonClicked(){
        editHeight.setVisibility(View.INVISIBLE);
        editWeight.setVisibility(View.INVISIBLE);
        heightView.setVisibility(View.VISIBLE);
        weightView.setVisibility(View.VISIBLE);
        editButton.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.INVISIBLE);

        this.height = Float.parseFloat(editHeight.getText().toString()); // update height
        this.weight = Float.parseFloat(editWeight.getText().toString()); // update weight

        User user = ((UserClient)(getActivity().getApplicationContext())).getUser();
        user.setHeight(this.height);
        user.setWeight(this.weight);
        ((UserClient)(getActivity().getApplicationContext())).setUser(user);
    }

    //@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout_button:
                signOut();
                break;
            case R.id.edit:
                editButtonClicked();
                updateViews();
                break;
            case R.id.save:
                saveButtonClicked();
                updateViews();
                break;
        }
    }

    private void calculateBMI(){
        this.BMI = this.weight / (this.height * this.height);
    }

    private void showSoftKeyboard(EditText text){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(text, InputMethodManager.SHOW_FORCED);
            imm.showSoftInput(text, InputMethodManager.SHOW_FORCED);
        }
    }
    private void hideSoftKeyboard(){
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void updateViews(){
        User user = ((UserClient)(getActivity().getApplicationContext())).getUser();
        editHeight.setText(Float.toString(user.getHeight()));
        editWeight.setText(Float.toString(user.getWeight()));
        heightView.setText(Float.toString(user.getHeight()) + " m");
        weightView.setText(Float.toString(user.getWeight()) + " kg");
        calculateBMI();
        BMIView.setText(Float.toString(this.BMI));
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
