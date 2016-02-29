package com.example.android.capstone.util;

import android.util.Log;

import com.firebase.client.Firebase;

/**
 * Created by baybora on 2/25/16.
 */
public class FirebaseUtil {

    public static final String LOG_TAG = FirebaseUtil.class.getSimpleName();

    public static Firebase mFireBase =null;

    public static void connect(){
        String fireBaseApp = "amber-inferno-2385";
        mFireBase = new Firebase("https://"+fireBaseApp+".firebaseio.com/");
    }

    public static void login(String email, String password, Firebase.AuthResultHandler authResultHandler){
        mFireBase.authWithPassword(email, password, authResultHandler);
        Log.i(LOG_TAG,email + " try to login");
    }


    public static void registerUser(String email, String password,Firebase.ResultHandler resultHandler){
        mFireBase.createUser(email, password, resultHandler);
        Log.i(LOG_TAG, email + " try to register");
    }

}
