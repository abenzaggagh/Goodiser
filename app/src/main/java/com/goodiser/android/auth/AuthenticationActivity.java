package com.goodiser.android.auth;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.goodiser.android.app.FeedActivity;
import com.goodiser.android.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_READ_CONTACTS = 0;

    private FirebaseAuth mAuth = null;

    // UI Elements
    private EditText mEmailView = null;
    private EditText mPasswordView = null;

    // User input
    protected String EMAIL = null;
    protected String PASSWORD = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();

        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

    }

    @Override
    public void onStart() {
        super.onStart();

        // mAuth.getCurrentUser()
    }

    private String getEmail() {
        return this.EMAIL;
    }
    private String getPassword() {
        return this.PASSWORD;
    }

    private boolean isEmailValid(String email) {
        return !email.isEmpty() && email.contains("@");
    }
    private boolean isPasswordValid(String password) {
        return !password.isEmpty() && password.length() > 8;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    private void signIn(View v) {

        this.EMAIL = mEmailView.getText().toString();
        this.PASSWORD = mPasswordView.getText().toString();

        //if (this.isEmailValid(EMAIL) && this.isPasswordValid(PASSWORD)) {
         //   hideSoftKeyboard(this);

            mAuth.signInWithEmailAndPassword(EMAIL, PASSWORD).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        FirebaseUser user = mAuth.getCurrentUser();

                        Intent intent = new Intent(AuthenticationActivity.this, FeedActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(AuthenticationActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }

                }
            });

       // }

    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.email_sign_in_button) {
            signIn(v);
        }
    }
}

