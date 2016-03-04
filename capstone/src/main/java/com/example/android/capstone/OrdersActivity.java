package com.example.android.capstone;

import android.app.LoaderManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.content.ContentProvider;
import com.example.android.capstone.components.location.DeliveryRouteLocationService;
import com.example.android.capstone.components.orders.OrdersSearchResultsCursorAdapter;
import com.example.android.capstone.components.widget.DeliveryRouteAppWidgetProvider;
import com.example.android.capstone.components.widget.DeliveryRouteAppWidgetService;
import com.example.android.capstone.util.Utils;
import com.example.android.firebase.FirebaseUtil;
import com.example.android.firebase.domain.DriversDomain;
import com.example.android.firebase.entity.OrderEntity;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import java.util.Date;

import butterknife.Bind;

/**
 * Created by baybora on 3/2/16.
 */
public class OrdersActivity extends com.example.android.capstone.BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_SEARCH_RESULTS = 1;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private OrdersSearchResultsCursorAdapter mAdapter;

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
        initLocationService();
    }


    private void setNameForUI() {
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DriversDomain driversDomain = FirebaseUtil.convertToDriversDomain(dataSnapshot);
                mName.setText(Utils.getString(getBaseContext(), R.string.hello) + driversDomain.getName());
                mDriverImageBase64.setImageBitmap(Utils.convertBase64ToImage(driversDomain.getImageBase64()));
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
        // create adapter
        mAdapter = new OrdersSearchResultsCursorAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        // start loader
        this.getLoaderManager().restartLoader(LOADER_SEARCH_RESULTS, null, this);



        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                FirebaseUtil.saveOrderEntity(dataSnapshot);

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

    private void updateWidget(){

        Intent intent = new Intent(getApplicationContext(),
                            DeliveryRouteAppWidgetService.class);

        int[] allWidgetIds = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), DeliveryRouteAppWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);
        startService(intent);
    }

    private void initLocationService(){

        Intent intent = new Intent(getApplicationContext(),
                DeliveryRouteLocationService.class);
        startService(intent);

    }


    @Override
    public Loader<Cursor> onCreateLoader(final int id, final Bundle args) {
        switch (id) {
            case LOADER_SEARCH_RESULTS:

                return new CursorLoader(OrdersActivity.this,
                        ContentProvider.createUri(OrderEntity.class, null),
                        null, null, null, null
                );
        }

        return null;
    }

    @Override
    public void onLoadFinished(final Loader<Cursor> loader, final Cursor data) {
        switch (loader.getId()) {
            case LOADER_SEARCH_RESULTS:

                mAdapter.swapCursor(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(final Loader<Cursor> loader) {
        switch (loader.getId()) {
            case LOADER_SEARCH_RESULTS:

                mAdapter.swapCursor(null);
                break;
        }
    }

}
