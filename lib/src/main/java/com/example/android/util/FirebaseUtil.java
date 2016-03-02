package com.example.android.util;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

/**
 * Created by baybora on 2/25/16.
 */
public class FirebaseUtil {

    public static final String LOG_TAG = FirebaseUtil.class.getSimpleName();

    public static Firebase mFireBase = null;

    public static void connect() {
        String fireBaseApp = "amber-inferno-2385";
        mFireBase = new Firebase("https://" + fireBaseApp + ".firebaseio.com/");
    }

    public static void login(String email, String password, Firebase.AuthResultHandler authResultHandler) {
        mFireBase.authWithPassword(email, password, authResultHandler);
    }


    public static void registerUser(String email, String password, Firebase.ResultHandler resultHandler) {
        mFireBase.createUser(email, password, resultHandler);
    }

    public static void getUserName(String email,ChildEventListener childEventListener) {
        Query query = mFireBase.child("drivers").orderByChild("email").equalTo(email);
        query.addChildEventListener(childEventListener);
    }

    public static void getOrders() {

    }

}
