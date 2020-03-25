package com.cz2006.fitflop.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cz2006.fitflop.R;
import com.cz2006.fitflop.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import es.dmoral.toasty.Toasty;

import static android.text.TextUtils.isEmpty;
import static com.cz2006.fitflop.util.Check.areStringsEqual;

public class RegisterView extends Fragment implements
        View.OnClickListener
{
    private static final String TAG = "RegisterActivity";

    //widgets
    private EditText mEmail, mPassword, mConfirmPassword;
    private ProgressBar mProgressBar;
    private TextView register_link, login_link;

    //vars
    private FirebaseFirestore mDb;

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @androidx.annotation.Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_view, container, false);
        context = getActivity().getApplicationContext();
        mEmail = (EditText) view.findViewById(R.id.input_email);
        mPassword = (EditText) view.findViewById(R.id.input_password);
        mConfirmPassword = (EditText) view.findViewById(R.id.input_confirm_password);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        register_link = view.findViewById(R.id.register_link);
        login_link = view.findViewById(R.id.login_link);
        register_link.setPaintFlags(login_link.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        register_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new RegisterView();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.fragment_login_reg,fragment);
                fragmentTransaction.commit();

                container.removeAllViews();
            }
        });

        login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new LoginView();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right, R.anim.enter_right_to_left, R.anim.exit_right_to_left);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.fragment_login_reg,fragment);
                fragmentTransaction.commit();
                container.removeAllViews();
            }
        });

        view.findViewById(R.id.btn_register).setOnClickListener(this);

        mDb = FirebaseFirestore.getInstance();

        hideSoftKeyboard();

        return view;
    }

    /**
     * Register a new email and password to Firebase Authentication
     * @param email
     * @param password
     */
    public void registerNewEmail(final String email, String password){

        showDialog();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()){
                            Log.d(TAG, "onComplete: AuthState: " + FirebaseAuth.getInstance().getCurrentUser().getUid());

                            //insert some default data
                            User user = new User();
                            user.setEmail(email);
                            user.setUsername(email.substring(0, email.indexOf("@")));
                            user.setUser_id(FirebaseAuth.getInstance().getUid());
                            user.setHeight(0.0f);
                            user.setWeight(0.0f);

                            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                                    .build();
                            mDb.setFirestoreSettings(settings);

                            DocumentReference newUserRef = mDb
                                    .collection(getString(R.string.collection_users))
                                    .document(FirebaseAuth.getInstance().getUid());

                            newUserRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    hideDialog();

                                    if(task.isSuccessful()){
                                        redirectLoginScreen();
                                    }else{
//                                        View parentLayout = findViewById(android.R.id.content);
                                        Toasty.error(getActivity(), "Something went wrong. Task not successful lvl 2", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                        else {
//                            View parentLayout = findViewById(android.R.id.content);
                            Toasty.error(getActivity(), "Something went wrong. Task not successful lvl 1", Toast.LENGTH_SHORT).show();
                            hideDialog();
                        }

                        // ...
                    }
                });
    }

    /**
     * Redirects the user to the login screen
     */
    private void redirectLoginScreen(){
        Log.d(TAG, "redirectLoginScreen: redirecting to login screen.");

        Intent intent = new Intent(getActivity(), LoginRegView.class);
        startActivity(intent);
        getActivity().finish();
    }


    private void showDialog(){
        mProgressBar.setVisibility(View.VISIBLE);

    }

    private void hideDialog(){
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void hideSoftKeyboard(){
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:{
                Log.d(TAG, "onClick: attempting to register.");

                //check for null valued EditText fields
                if(!isEmpty(mEmail.getText().toString())
                        && !isEmpty(mPassword.getText().toString())
                        && !isEmpty(mConfirmPassword.getText().toString())){

                    //check if passwords match
                    if(areStringsEqual(mPassword.getText().toString(), mConfirmPassword.getText().toString())){

                        //Initiate registration task
                        registerNewEmail(mEmail.getText().toString(), mPassword.getText().toString());
                    }else{
                        Toasty.warning(getActivity(), "Passwords do not Match", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toasty.warning(getActivity(), "You must fill out all the fields", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }
}
