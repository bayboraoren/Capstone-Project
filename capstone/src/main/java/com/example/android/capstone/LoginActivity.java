package com.example.android.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.firebase.FirebaseUtil;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import butterknife.Bind;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends com.example.android.capstone.BaseActivity implements Firebase.AuthResultHandler{

    @Bind(R.id.email_sign_in_button)
    Button emailSignInButton;

    @Bind(R.id.email)
    TextView email;

    @Bind(R.id.password)
    TextView password;

    @Bind(R.id.register_button)
    FloatingActionButton registerButton;

    public LoginActivity(){
        super(LoginActivity.class.getSimpleName(),"LOGIN");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFireBase();
        initLoginActivity();
    }


    private void initFireBase() {
        Firebase.setAndroidContext(this);
        FirebaseUtil.connect();
    }

    private void initLoginActivity() {
        initLayout(R.layout.activity_login,LAYOUT_TITLE,false,"");
        initBindView();
        initEmailSignInButton(this);
        initRegisterButton(this);
    }

    private void initEmailSignInButton(final LoginActivity loginActivity){

        emailSignInButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseUtil.login(email.getText().toString(), password.getText().toString(), loginActivity);
                    }
                }
        );
    }

    private void initRegisterButton(final LoginActivity loginActivity){
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity, com.example.android.capstone.RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
    }


    @Override
    public void onAuthenticated(AuthData authData) {
        Log.i(LOG_TAG, "User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
        Intent intent = new Intent(this, com.example.android.capstone.OrdersActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

    }

    @Override
    public void onAuthenticationError(FirebaseError firebaseError) {
        Log.e(LOG_TAG, firebaseError.getMessage() + " " + firebaseError.getDetails());
        Toast.makeText(LoginActivity.this, "YOU CAN NOT LOGGED IN", Toast.LENGTH_SHORT).show();
    }



}

