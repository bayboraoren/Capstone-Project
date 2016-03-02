package com.example.android.firebase;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

import java.util.HashMap;

import com.example.android.firebase.util.DriversDomain;
import com.example.android.firebase.util.OrdersDomain;

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

    public static void getOrders(ChildEventListener childEventListener) {
        Query query = mFireBase.child("orders");
        query.addChildEventListener(childEventListener);
    }

    public static DriversDomain convertToDriversDomain(DataSnapshot dataSnapshot){
        DriversDomain driversDomain = new DriversDomain();
        HashMap hashMap = ((HashMap)dataSnapshot.getValue());
        driversDomain.setName((String) hashMap.get(DriversDomain.NAME));
        driversDomain.setSurname((String) hashMap.get(DriversDomain.SURNAME));
        driversDomain.setEmail((String) hashMap.get(DriversDomain.EMAIL));
        driversDomain.setImageBase64((String) hashMap.get(DriversDomain.IMAGE_BASE_64));
        return driversDomain;
    }

    public static OrdersDomain convertToOrdersDomainList(DataSnapshot dataSnapshot){

        HashMap hashMap = ((HashMap)dataSnapshot.getValue());

        OrdersDomain ordersDomain = new OrdersDomain();
        ordersDomain.setName((String)hashMap.get(OrdersDomain.NAME));
        ordersDomain.setImageBase64((String) hashMap.get(OrdersDomain.IMAGE_BASE_64));
        ordersDomain.setCustomer((String) hashMap.get(OrdersDomain.CUSTOMER));
        ordersDomain.setLongitude((String) hashMap.get(OrdersDomain.LONGITUDE));
        ordersDomain.setLatitude((String) hashMap.get(OrdersDomain.LATITUDE));

        return ordersDomain;
    }


}
