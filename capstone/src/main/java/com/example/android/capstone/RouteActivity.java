package com.example.android.capstone;

import android.os.Bundle;

import com.example.android.util.FirebaseUtil;
import com.firebase.client.Firebase;

/**
 * Created by baybora on 3/2/16.
 */
public class RouteActivity extends com.example.android.capstone.BaseActivity{

    public RouteActivity(){
        super(RouteActivity.class.getSimpleName(),"ROUTE");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFireBase();
        initRouteActivity();
    }


    private void initFireBase() {
        Firebase.setAndroidContext(this);
        FirebaseUtil.connect();
    }

    private void initRouteActivity() {
        initLayout(R.layout.activity_route,LAYOUT_TITLE,true,OrdersActivity.LAYOUT_TITLE);
        initBindView();
    }

}
