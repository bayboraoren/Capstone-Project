package com.example.android.sunshine.capstone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.sunshine.capstone.util.FireBaseUtil;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements Firebase.AuthResultHandler{

    public static final String LOG_TAG = LoginActivity.class.getSimpleName();

    private Toolbar toolbar;


    @Bind(R.id.email_sign_in_button)
    Button emailSignInButton;

    @Bind(R.id.email)
    TextView email;

    @Bind(R.id.password)
    TextView password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFireBase();
        initLoginActivity();
    }


    private void initBindView(){
        ButterKnife.bind(this);
    }


    private void initFireBase() {
        Firebase.setAndroidContext(this);
        FireBaseUtil.connect();
    }

    private void initLoginActivity() {
        initLayout();
        initBindView();
        initEmailSignInButton(this);
    }



    private void initLayout(){
        setContentView(R.layout.activity_login);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
    }

    private void initEmailSignInButton(final LoginActivity loginActivity){

        emailSignInButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FireBaseUtil.login(email.getText().toString(), password.getText().toString(), loginActivity);
                    }
                }
        );
    }


    @Override
    public void onAuthenticated(AuthData authData) {
        Log.i(LOG_TAG, "User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
        Toast.makeText(LoginActivity.this, "YOU LOGGED IN", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationError(FirebaseError firebaseError) {
        Log.e(LOG_TAG, firebaseError.getMessage() + " " + firebaseError.getDetails());
        Toast.makeText(LoginActivity.this, "YOU CAN NOT LOGGED IN", Toast.LENGTH_SHORT).show();
    }



}

