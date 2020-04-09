package com.cz2006.fitflop.ui;

import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cz2006.fitflop.R;
import com.cz2006.fitflop.UserClient;
import com.cz2006.fitflop.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import es.dmoral.toasty.Toasty;

import static android.text.TextUtils.isEmpty;

/**
 * Fragment for Login Screen
 */
public class LoginFragment extends Fragment implements
        View.OnClickListener
{

    private static final String TAG = "LoginActivity";

    //Firebase
    private FirebaseAuth.AuthStateListener mAuthListener;

    // widgets
    private EditText mEmail, mPassword;
    private ProgressBar mProgressBar;
    private TextView register_link, login_link;

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_view, container, false);

        context = getActivity().getApplicationContext();
        mEmail = view.findViewById(R.id.email_login);
        mPassword = view.findViewById(R.id.password_login);
        mProgressBar = view.findViewById(R.id.progressBar);
        register_link = view.findViewById(R.id.register_link);
        login_link = view.findViewById(R.id.login_link);
        login_link.setPaintFlags(login_link.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        register_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new RegisterFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.fragment_login_reg,fragment);
                fragmentTransaction.commit();
                container.removeAllViews();
            }
        });

        login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new LoginFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.fragment_login_reg,fragment);
                fragmentTransaction.commit();
                container.removeAllViews();
            }
        });

        setupFirebaseAuth();
        view.findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        view.findViewById(R.id.forget_password_link).setOnClickListener(this);

        hideSoftKeyboard();

        return view;
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

    /*
        ----------------------------- Firebase setup ---------------------------------
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: started.");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toasty.success(getActivity(), "Authenticated with: " + user.getEmail(), Toast.LENGTH_SHORT).show();

                    // can comment start -> ((UserClient)(getApplicationContext())).setUser(user);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                            .build();
                    db.setFirestoreSettings(settings);

                    DocumentReference userRef = db.collection(getString(R.string.collection_users))
                            .document(user.getUid());

                    userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                Log.d(TAG, "onComplete: successfully set the user client.");
                                User user = task.getResult().toObject(User.class);
                                ((UserClient)(context)).setUser(user);
                            }
                        }
                    });
                    // can comment end -> ((UserClient)(getApplicationContext())).setUser(user);

                    Intent intent = new Intent(getActivity(), BaseActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    getActivity().finish();

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }

    private void signIn(){
        //check if the fields are filled out
        if(!isEmpty(mEmail.getText().toString())
                && !isEmpty(mPassword.getText().toString())){
            Log.d(TAG, "onClick: attempting to authenticate.");

            showDialog();

            FirebaseAuth.getInstance().signInWithEmailAndPassword(mEmail.getText().toString(),
                    mPassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            hideDialog();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toasty.error(getActivity(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                    hideDialog();
                }
            });
        } else {
            Toasty.warning(getActivity(), "You didn't fill in all the fields.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.email_sign_in_button:{
                signIn();
                break;
            }

            case R.id.forget_password_link:{
                final EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter Your Email To Received Reset Link. ");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link

                        String mail = resetMail.getText().toString();
                        FirebaseAuth.getInstance().sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toasty.success(getActivity(), "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toasty.error(getActivity(), "Error! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton( "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close the dialog
                    }
                });

                passwordResetDialog.create().show();
            }
        }
    }

}