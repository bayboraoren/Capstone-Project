package com.example.android.capstone;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.capstone.util.Utils;
import com.example.android.util.FirebaseUtil;
import com.example.android.util.domain.DriversDomain;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import java.util.Date;

import butterknife.Bind;

/**
 * Created by baybora on 3/2/16.
 */
public class OrdersActivity extends com.example.android.capstone.BaseActivity{

    @Bind(R.id.name)
    TextView name;

    @Bind(R.id.date)
    TextView date;

    @Bind(R.id.driver_imagebase64)
    ImageView driverImageBase64;

    public OrdersActivity() {
        super(OrdersActivity.class.getSimpleName(), "ORDERS");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initOrdersActivity();
    }


    public void initOrdersActivity(){
        initLayout(R.layout.activity_orders, LAYOUT_TITLE, false, "");
        initBindView();
        setNameForUI();
        setDateForUI();
    }

    private void setNameForUI(){
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DriversDomain driversDomain = FirebaseUtil.convertToDriversDomain(dataSnapshot);
                name.setText(Utils.getString(getBaseContext(), R.string.hello) + driversDomain.getName());
                driverImageBase64.setImageBitmap(Utils.convertImageToBase64(driversDomain.getImageBase64()));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };


        FirebaseUtil.getUserName("baybora.oren@gmail.com", childEventListener);
    }

    private void setDateForUI(){
        date.setText(new Date().toString());
    }


}
