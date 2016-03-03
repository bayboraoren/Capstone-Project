package com.example.android.capstone;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.capstone.components.orders.OrdersRecyclerAdapter;
import com.example.android.capstone.util.Utils;
import com.example.android.firebase.FirebaseUtil;
import com.example.android.firebase.domain.DriversDomain;
import com.example.android.firebase.domain.OrdersDomain;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import java.util.Date;

import butterknife.Bind;

/**
 * Created by baybora on 3/2/16.
 */
public class OrdersActivity extends com.example.android.capstone.BaseActivity {

    private RecyclerView mRecyclerView;
    private OrdersRecyclerAdapter mOrdersAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Bind(R.id.name)
    TextView mName;

    @Bind(R.id.date)
    TextView mDate;

    @Bind(R.id.driver_imagebase64)
    ImageView mDriverImageBase64;

    public OrdersActivity() {
        super(OrdersActivity.class.getSimpleName(), "ORDERS");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initOrdersActivity();
    }


    public void initOrdersActivity() {
        initLayout(R.layout.activity_orders, LAYOUT_TITLE, false, "");
        initBindView();
        setNameForUI();
        setDateForUI();
        initOrdersRecyclerView();
    }


    private void setNameForUI() {
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DriversDomain driversDomain = FirebaseUtil.convertToDriversDomain(dataSnapshot);
                mName.setText(Utils.getString(getBaseContext(), R.string.hello) + driversDomain.getName());
                mDriverImageBase64.setImageBitmap(Utils.convertImageToBase64(driversDomain.getImageBase64()));
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

    private void setDateForUI() {
        mDate.setText(new Date().toString());
    }


    private void initOrdersRecyclerView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.orders_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mOrdersAdapter = new OrdersRecyclerAdapter(this);
        mRecyclerView.setAdapter(mOrdersAdapter);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                OrdersDomain ordersDomain = FirebaseUtil.convertToOrdersDomainList(dataSnapshot);
                mOrdersAdapter.add(ordersDomain);

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

        FirebaseUtil.getOrders(childEventListener);

    }

}
