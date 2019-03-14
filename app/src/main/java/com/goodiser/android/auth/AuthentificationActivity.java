package com.goodiser.android.auth;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.goodiser.android.app.FeedActivity;
import com.goodiser.android.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;


public class AuthentificationActivity extends AppCompatActivity {

    private static final int REQUEST_READ_CONTACTS = 0;

    private FirebaseAuth mAuth = null;

    private AutoCompleteTextView mEmailView = null;
    private EditText mPasswordView = null;

    protected String EMAIL = null;
    protected String PASSWORD = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        this.EMAIL = mEmailView.getText().toString();
        this.PASSWORD = mPasswordView.getText().toString();

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feedIntent = new Intent(view.getContext(), FeedActivity.class);
                startActivity(feedIntent);



            }
        });



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
        return email.contains("@");
    }
    private boolean isPasswordValid(String password) {
        return password.length() > 8;
    }




}

