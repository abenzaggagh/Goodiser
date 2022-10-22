package com.goodiser.android.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.goodiser.android.app.FeedActivity;
import com.goodiser.android.R;
import com.goodiser.modal.Database;
import com.goodiser.modal.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth = null;
    private FirebaseFirestore mDatabase = null;

    private ConstraintLayout mSignInView = null;
    private ConstraintLayout mSignUpView = null;
    private ConstraintLayout mForgetView = null;

    private TextInputLayout mEmailLayout = null;
    private TextInputLayout mPasswordLayout = null;

    private EditText mNameView = null;
    private EditText mPhoneView = null;

    private EditText mEmailView = null;
    private EditText mPasswordView = null;

    private Button mSignInButton = null;
    private ProgressBar mSignInProgress = null;

    private Button mSignUpButton = null;
    private ProgressBar mSignUpProgress = null;

    private Button forgetPassword = null;
    private Button mRestPassword = null;

    private Button mBack;
    private Button mToSignUp;
    private Button mToSignIn;

    private LoginButton loginButton;

    private LinearLayout mNotification;
    private ImageButton mCloseNotification;

    protected String NAME = null;
    protected String EMAIL = null;
    protected String PHONE = null;
    protected String PASSWORD = null;

    private Boolean mFacebookAuth = false;
    private CallbackManager mCallbackManager;
    private LoginResult mLoginResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        // FacebookSdk.sdkInitialize(getApplicationContext());
        // AppEventsLogger.activateApp(this);

        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(AuthenticationActivity.this, FeedActivity.class));
            finish();
        }

        initializeWidget();

        initializeOnClickListeners();

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {

        if (mSignUpView.getVisibility() == View.VISIBLE && (mSignUpProgress.getVisibility() == View.GONE || mSignInProgress.getVisibility() == View.GONE)) {
            toSignIn();
        } else if (mSignInView.getVisibility() == View.VISIBLE && (mSignUpProgress.getVisibility() == View.GONE || mSignInProgress.getVisibility() == View.GONE)) {
            toSignUp();
        } else if (mForgetView.getVisibility() == View.VISIBLE && (mSignUpProgress.getVisibility() == View.GONE || mSignInProgress.getVisibility() == View.GONE)) {
            mForgetView.setVisibility(View.GONE);
            mSignInView.setVisibility(View.VISIBLE);
        }

    }

    public void initializeWidget() {

        mSignInView = (ConstraintLayout) findViewById(R.id.sign_in_layout);
        mSignUpView = (ConstraintLayout) findViewById(R.id.sign_up_layout);
        mForgetView = (ConstraintLayout) findViewById(R.id.forget_layout);

        mNotification = (LinearLayout) findViewById(R.id.notification);
        mCloseNotification = (ImageButton) findViewById(R.id.close_notif);

        mEmailLayout = (TextInputLayout) findViewById(R.id.email_input_layout);
        mPasswordLayout = (TextInputLayout) findViewById(R.id.password_input_layout);

        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInProgress = (ProgressBar) findViewById(R.id.sign_in_progress);

        forgetPassword = (Button) findViewById(R.id.forgot_password);

        mSignUpButton = (Button) findViewById(R.id.sign_up_button);
        mSignUpProgress = (ProgressBar) findViewById(R.id.sign_up_progress);

        mRestPassword = (Button) findViewById(R.id.rest_password);

        mToSignUp = (Button) findViewById(R.id.to_sign_up);
        mToSignIn = (Button) findViewById(R.id.to_sign_in);
        mBack = (Button) findViewById(R.id.forget_back);

        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");

    }

    public void initializeOnClickListeners() {

        mSignInButton.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);
        mRestPassword.setOnClickListener(this);

        mToSignIn.setOnClickListener(this);
        mToSignUp.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mCloseNotification.setOnClickListener(this);

    }

    public void toSignUp() {

        mNameView = (EditText) findViewById(R.id.name_sign_up);
        mEmailView = (EditText) findViewById(R.id.email_sign_up);
        mPhoneView = (EditText) findViewById(R.id.phone_sign_up);
        mPasswordView = (EditText) findViewById(R.id.password_sign_up);

        mSignInView.setVisibility(View.GONE);
        mSignUpView.setVisibility(View.VISIBLE);

    }

    public void toSignIn() {

        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        mSignInView.setVisibility(View.VISIBLE);
        mSignUpView.setVisibility(View.GONE);

    }

    public void toForget() {

        mForgetView.setVisibility(View.VISIBLE);
        mSignInView.setVisibility(View.GONE);

        mEmailView = (EditText) findViewById(R.id.forget_email);

    }

    public void back() {

        mForgetView.setVisibility(View.GONE);
        mSignInView.setVisibility(View.VISIBLE);

        mEmailView = (EditText) findViewById(R.id.email);

    }

    public void closeNotification() {

        mNotification.setVisibility(View.GONE);

    }

    private boolean isEmailValid(String email) {
        return !email.isEmpty() && email.contains("@");
    }
    private boolean isPasswordValid(String password) { return !password.isEmpty() && password.length() > 6; }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void signIn(View view) {

        EMAIL = mEmailView.getText().toString();
        PASSWORD = mPasswordView.getText().toString();

        mSignInProgress.setVisibility(View.VISIBLE);
        mSignInButton.setVisibility(View.GONE);

        if (this.EMAIL.isEmpty()) {
            mEmailLayout.setError("This field can't be empty.");
        } else {
            mEmailLayout.setErrorEnabled(false);
        }

        if (!this.EMAIL.contains("@")) {
            mEmailLayout.setError("Email is incorrect.");
        } else {
            mEmailLayout.setErrorEnabled(false);
        }

        if (this.PASSWORD.isEmpty()) {
            mPasswordLayout.setError("This field can't be empty.");
        } else {
            mEmailLayout.setErrorEnabled(false);
        }

        if (this.isEmailValid(EMAIL) && this.isPasswordValid(PASSWORD)) {

            hideSoftKeyboard(this);

            mAuth.signInWithEmailAndPassword(EMAIL, PASSWORD).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        FirebaseUser user = mAuth.getCurrentUser();

                        Intent intent = new Intent(AuthenticationActivity.this, FeedActivity.class);
                        startActivity(intent);
                        finish();

                    } else {

                        mNotification.setVisibility(View.VISIBLE);

                        mSignInProgress.setVisibility(View.GONE);
                        mSignInButton.setVisibility(View.VISIBLE);

                    }
                }
            });

        } else {

            mSignInProgress.setVisibility(View.GONE);
            mSignInButton.setVisibility(View.VISIBLE);

        }

    }

    public void signUp(View view) {

        NAME = mNameView.getText().toString();
        EMAIL = mEmailView.getText().toString();
        PHONE = mPhoneView.getText().toString();
        PASSWORD = mPasswordView.getText().toString();


        mSignUpButton.setVisibility(View.GONE);
        mSignUpProgress.setVisibility(View.VISIBLE);

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

                            mUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(AuthenticationActivity.this, "Verification Email has been sent", Toast.LENGTH_LONG).show();

                                        startActivity(new Intent(AuthenticationActivity.this, FeedActivity.class));
                                        finish();

                                    }
                                }
                            });

                        } else {

                            Log.e("sign_up", "User not signed up");

                            mSignUpButton.setVisibility(View.VISIBLE);
                            mSignUpProgress.setVisibility(View.GONE);

                        }

                    }
                });

    }

    public void forgotPassword(View view) {

        this.EMAIL = mEmailView.getText().toString();

        if (this.isEmailValid(EMAIL)) {

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    mAuth.sendPasswordResetEmail(EMAIL).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AuthenticationActivity.this, "Password Rest Email has been sent", Toast.LENGTH_LONG).show();
                                back();
                            } else {
                                Toast.makeText(AuthenticationActivity.this, "Please enter a valid or existing email", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });

        } else {
            Toast.makeText(AuthenticationActivity.this, "The email address is required", Toast.LENGTH_LONG).show();
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();

                            Intent intent = new Intent(AuthenticationActivity.this, FeedActivity.class);
                            startActivity(intent);
                            finish();

                        } else {

                        }

                    }
                });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.sign_in_button:
                signIn(v);
                break;

            case R.id.sign_up_button:
                signUp(v);
                break;

            case R.id.forgot_password:
                toForget();
                break;

            case R.id.rest_password:
                forgotPassword(v);
                break;

            case R.id.to_sign_in:
                toSignIn();
                break;

            case R.id.to_sign_up:
                toSignUp();
                break;

            case R.id.forget_back:
                back();
                break;

            case R.id.close_notif:
                closeNotification();
                break;

            case R.id.login_button:

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                // Log.d("FB", "facebook:onSuccess:" + loginResult);
                                mLoginResult = loginResult;
                                mFacebookAuth = true;
                            }

                            @Override
                            public void onCancel() {
                                Log.d("FB", "facebook:onCancel");
                            }

                            @Override
                            public void onError(FacebookException error) {
                                Log.d("FB", "facebook:onError", error);
                            }

                        });
                    }
                });

                if (mFacebookAuth) {
                    handleFacebookAccessToken(mLoginResult.getAccessToken());
                }

                break;

        }

    }


}

