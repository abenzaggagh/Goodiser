package com.goodiser.android.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.goodiser.android.app.FeedActivity;
import com.goodiser.android.R;
import com.goodiser.modal.Database;
import com.goodiser.modal.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class AuthenticationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth = null;
    private FirebaseFirestore mDatabase = null;

    // UI Forms
    private ConstraintLayout mSignInView = null;
    private ScrollView mSignUpView = null;

    /**
     * UI Elements
     *
     * Email TextField
     * Password TextField
     *
     */
    private EditText mEmailView = null;
    private EditText mPasswordView = null;

    private EditText mNameView = null;
    private EditText mPhoneView = null;

    private Button mSignInButton = null;
    private ProgressBar mSignInProgress = null;

    // UI Notifications
    private ImageView mNotificationView = null;
    private TextView mNotificationText = null;


    // Changing Layout Buttons
    private Button mToSignUp;
    private Button mToSignIn;

    // User Data
    protected String NAME = null;
    protected String EMAIL = null;
    protected String PHONE = null;
    protected String PASSWORD = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        mAuth = FirebaseAuth.getInstance();

        mSignInProgress = (ProgressBar) findViewById(R.id.signin_progress);
        mSignInButton = (Button) findViewById(R.id.sign_in_button);

        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        mNotificationView = (ImageView) findViewById(R.id.notification_view);
        mNotificationText = (TextView) findViewById(R.id.notification_text);

        mSignInView = (ConstraintLayout) findViewById(R.id.signin_form);
        // mSignUpView = (ScrollView) findViewById(R.id.signup_form);

        mToSignUp = (Button) findViewById(R.id.to_sign_up);
        // mToSignIn = (Button) findViewById(R.id.to_sign_in);

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(AuthenticationActivity.this, FeedActivity.class));
        }

        mToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSignUp();
            }
        });

        //mToSignIn.setOnClickListener(new View.OnClickListener() {
        //   @Override
        //  public void onClick(View v) {
            //      toSignIn();
            //    }
        //});

    }

    @Override
    public void onStart() {
        super.onStart();


    }



    public void toSignUp() {

        //mNameView = (EditText) findViewById(R.id.name_signup);
        //mEmailView = (EditText) findViewById(R.id.email_signup);
        //mPhoneView = (EditText) findViewById(R.id.phone_signup);
        //mPasswordView = (EditText) findViewById(R.id.password_signup);

        //mSignInView.setVisibility(View.GONE);
        //mSignUpView.setVisibility(View.VISIBLE);
    }

    public void toSignIn() {

        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        mSignInView.setVisibility(View.VISIBLE);
        mSignUpView.setVisibility(View.GONE);

    }

    private boolean isEmailValid(String email) {
        return !email.isEmpty() && email.contains("@");
    }
    private boolean isPasswordValid(String password) {
        return !password.isEmpty() && password.length() > 6;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * Authentication Methods
     *
     * Sign In
     * Sign Up
     *
     */

    public void signIn(View view) {

        EMAIL = mEmailView.getText().toString();
        PASSWORD = mPasswordView.getText().toString();

        mSignInProgress.setVisibility(View.VISIBLE);
        mSignInButton.setVisibility(View.GONE);


        if (this.isEmailValid(EMAIL) && this.isPasswordValid(PASSWORD)) {

            hideSoftKeyboard(this);

            mAuth.signInWithEmailAndPassword(EMAIL, PASSWORD).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        FirebaseUser user = mAuth.getCurrentUser();

                        Intent intent = new Intent(AuthenticationActivity.this, FeedActivity.class);
                        startActivity(intent);

                    } else {

                        new CountDownTimer(3000, 1000){
                            public void onTick(long millisUntilFinished){
                                mNotificationView.setVisibility(View.VISIBLE);
                                mNotificationText.setVisibility(View.VISIBLE);
                                mNotificationText.setText("Your information are incorrect.");
                            }
                            public  void onFinish(){
                                mNotificationView.setVisibility(View.GONE);
                                mNotificationText.setVisibility(View.GONE);
                                mNotificationText.setText("");
                            }
                        }.start();

                        mSignInProgress.setVisibility(View.GONE);
                        mSignInButton.setVisibility(View.VISIBLE);

                    }

                }
            });

        } else {

            mSignInProgress.setVisibility(View.GONE);
            mSignInButton.setVisibility(View.VISIBLE);

            new CountDownTimer(3000, 1000){
                public void onTick(long millisUntilFinished){
                    mNotificationView.setVisibility(View.VISIBLE);
                    mNotificationText.setVisibility(View.VISIBLE);
                    mNotificationText.setText("Your information are invalid.");
                }
                public  void onFinish(){
                    mNotificationView.setVisibility(View.GONE);
                    mNotificationText.setVisibility(View.GONE);
                    mNotificationText.setText("");
                }
            }.start();

        }

    }



    public void signUp(View view) {

        NAME = mNameView.getText().toString();
        EMAIL = mEmailView.getText().toString();
        PHONE = mPhoneView.getText().toString();
        PASSWORD = mPasswordView.getText().toString();

        mAuth.createUserWithEmailAndPassword(EMAIL, PASSWORD)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser mUser = mAuth.getCurrentUser();

                            String UID = mUser.getUid();

                            User user = new User();

                            user.setUID(UID);
                            user.setName(NAME);
                            user.setEmail(EMAIL);
                            user.setPhone(PHONE);

                            Database.insertUser(user);

                            startActivity(new Intent(AuthenticationActivity.this, FeedActivity.class));

                        } else {





                        }

                    }
                });

    }










}

