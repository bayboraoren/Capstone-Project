package com.example.android.capstone;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.util.FirebaseUtil;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import butterknife.Bind;

/**
 * Created by baybora on 2/29/16.
 */
public class RegisterActivity extends com.example.android.capstone.BaseActivity implements Firebase.ResultHandler{

    @Bind(R.id.email)
    TextView email;

    @Bind(R.id.email_repeat)
    TextView emailRepeat;


    @Bind(R.id.password)
    TextView password;

    @Bind(R.id.email_register_button)
    Button registerButton;


    public RegisterActivity(){
        super(RegisterActivity.class.getSimpleName(),"Register");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRegisterActivity();
    }



    private void initRegisterActivity() {
        initLayout(R.layout.activity_register, LAYOUT_TITLE, true, com.example.android.capstone.LoginActivity.LAYOUT_TITLE);
        initBindView();
        initRegisterButton(this);
    }

    private void initRegisterButton(final RegisterActivity registerActivity){
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUtil.registerUser(email.getText().toString(), password.getText().toString(), registerActivity);
            }
        });
    }


    @Override
    public void onSuccess() {
        AuthData authData = FirebaseUtil.mFireBase.getAuth();
        String uid = authData.getUid();
        String provider = authData.getProvider();
        Log.i(LOG_TAG, "User ID: " + uid + ", Provider: " + provider);
        Toast.makeText(RegisterActivity.this, "YOU REGISTERED", Toast.LENGTH_SHORT).show();

        finish();
    }

    @Override
    public void onError(FirebaseError firebaseError) {
        Log.e(LOG_TAG, firebaseError.getMessage() + " " + firebaseError.getDetails());
        Toast.makeText(RegisterActivity.this, "YOU CAN NOT REGISTERED : " + firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
