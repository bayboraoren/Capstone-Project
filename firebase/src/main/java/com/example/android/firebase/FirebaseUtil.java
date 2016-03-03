package com.example.android.firebase;

import com.example.android.firebase.domain.DriversDomain;
import com.example.android.firebase.domain.OrdersDomain;
import com.example.android.firebase.entity.LocationEntity;
import com.example.android.firebase.entity.OrderEntity;
import com.example.android.firebase.entity.OrderEntityHelper;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

import java.util.HashMap;

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
        mFireBase.unauth();
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
        ordersDomain.setName((String)hashMap.get(OrderEntityHelper.NAME));
        ordersDomain.setImageBase64((String) hashMap.get(OrderEntityHelper.IMAGE_BASE_64));
        ordersDomain.setCustomer((String) hashMap.get(OrderEntityHelper.CUSTOMER));
        ordersDomain.setLongitude((String) hashMap.get(OrderEntityHelper.LONGITUDE));
        ordersDomain.setLatitude((String) hashMap.get(OrderEntityHelper.LATITUDE));

        return ordersDomain;
    }

    public static OrderEntity saveOrderEntity(DataSnapshot dataSnapshot){

        HashMap hashMap = ((HashMap)dataSnapshot.getValue());

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setName((String)hashMap.get(OrderEntityHelper.NAME));
        orderEntity.setImageBase64((String) hashMap.get(OrderEntityHelper.IMAGE_BASE_64));
        orderEntity.setCustomer((String) hashMap.get(OrderEntityHelper.CUSTOMER));
        orderEntity.setLocationEntity(saveLocationEntity(dataSnapshot));
        orderEntity.save();

        return orderEntity;
    }

    public static LocationEntity saveLocationEntity(DataSnapshot dataSnapshot){
        HashMap hashMap = ((HashMap)dataSnapshot.getValue());

        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setLatitude((String) hashMap.get(OrderEntityHelper.LATITUDE));
        locationEntity.setLongitude((String) hashMap.get(OrderEntityHelper.LONGITUDE));
        locationEntity.save();

        return locationEntity;
    }


}
