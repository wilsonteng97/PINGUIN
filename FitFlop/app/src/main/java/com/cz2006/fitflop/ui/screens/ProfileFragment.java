package com.cz2006.fitflop.ui.screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Initialises the contents in the Profile Fragment (for the profile screen)
 */
public class ProfileFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "Profile Activity";

    private String name, email, BMI_string;
    private TextView nameView, emailView, BMIView, heightView, weightView;
    private EditText editHeight, editWeight;
    private float height, weight, BMI;
    private Button logoutButton;
    private Button editButton, saveButton;

    /**
     * Method to initialise the view when fragment is first created
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(activity_profiles, container, false);

        /**
         * Checks for the current user information
         */
        User user = ((UserClient)(getActivity().getApplicationContext())).getUser();
        if (user==null) {
            user = new User("TestEmail@mail.com", "3mTjQ1eGZEfLHrqNqka2cLk3Qui2", "TestEmail", "test_avatar", 1.75f, 65);
        }

        /**
         * Retrieves user information from the User class and initialise variables
         */
        name = user.getUsername();
        email = user.getEmail();
        height = user.getHeight();
        weight = user.getWeight();
        calculateBMI();

        /**
         * Initialise views for the layout xml file
         */
        nameView = (TextView)view.findViewById(R.id.userName);
        emailView = (TextView)view.findViewById(R.id.emailAddress);
        BMIView = (TextView)view.findViewById(R.id.calculated_BMI);
        heightView = (TextView)view.findViewById(R.id.height_in_cm);
        weightView = (TextView)view.findViewById(R.id.weight_in_kg);
        editHeight = (EditText) view.findViewById(R.id.editHeight);
        editWeight = (EditText) view.findViewById(R.id.editWeight);

        /**
         * Set data to display in each of the views
          */
        nameView.setText(name);
        emailView.setText(email);
        updateViews();

        /**
         * Initialise buttons
         */
        logoutButton = (Button) view.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(this);
        editButton = (Button) view.findViewById(R.id.edit);
        saveButton = (Button) view.findViewById(R.id.save);
        editButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);

        user.setHeight(height);
        user.setWeight(weight);

        return view;
    }

    /**
     * Method to log out the current user
     */
    private void signOut(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(), LoginRegView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    /**
     * When edit button is clicked, show the editText views and hide the textViews to enable editing of height and weight
     */
    private void editButtonClicked(){
        editHeight.setVisibility(View.VISIBLE);
        editWeight.setVisibility(View.VISIBLE);
        heightView.setVisibility(View.INVISIBLE);
        weightView.setVisibility(View.INVISIBLE);
        editButton.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.VISIBLE);

        showSoftKeyboard(editHeight);
    }

    /**
     * Store the updated user height and weight when the save button is clicked
     */
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

    /**
     * Switch function to control button click activity
     * @param v
     */
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

    /**
     * Function to calculate BMI
     */
    private void calculateBMI(){
        this.BMI = this.weight / (this.height/100 * this.height/100);
    }

    /**
     * Function to display the keyboard to edit text
     * @param text
     */
    private void showSoftKeyboard(EditText text){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(text, InputMethodManager.SHOW_FORCED);
            imm.showSoftInput(text, InputMethodManager.SHOW_FORCED);
        }
    }

    /**
     * Update the editText and viewText views whenever height and weight is edited, to display the updated information on the screen
     */
    private void updateViews(){
        User user = ((UserClient)(getActivity().getApplicationContext())).getUser();
        editHeight.setText(Float.toString(user.getHeight()));
        editWeight.setText(Float.toString(user.getWeight()));
        heightView.setText(Float.toString(user.getHeight()));
        weightView.setText(Float.toString(user.getWeight()));
        calculateBMI();
        BMIView.setText(Float.toString(this.BMI));
    }

}
