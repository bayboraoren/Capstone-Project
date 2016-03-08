package com.example.android.capstone;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.content.ContentProvider;
import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.android.capstone.util.Utils;
import com.example.android.firebase.FirebaseUtil;
import com.example.android.firebase.entity.OrderEntity;
import com.example.android.firebase.entity.OrderEntityHelper;
import com.firebase.client.Firebase;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by baybora on 3/2/16.
 */
public class RouteActivity extends com.example.android.capstone.BaseActivity implements DirectionCallback,LoaderManager.LoaderCallbacks<Cursor>{

    private static final int LOADER_SEARCH_RESULTS = 1;

    @Bind(R.id.order_imagebase64)
    ImageView orderImageBase64;

    @Bind(R.id.order_name)
    TextView orderName;

    @Bind(R.id.customer_name)
    TextView customerName;

    @Bind(R.id.order_distance_km)
    TextView orderDistanceKM;

    @Bind(R.id.delivered)
    Button delivered;

    private RouteActivity mActivity;

    private OrderEntity orderEntity;

    //map
    private GoogleMap mGoogleMap;
    private Polyline mPolyline;
    private LatLng myLatLng;
    private LatLng toLatLng;

    public RouteActivity() {
        super(RouteActivity.class.getSimpleName(), "ROUTE");
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
        initLayout(R.layout.activity_route, LAYOUT_TITLE, true, OrdersActivity.LAYOUT_TITLE);
        initBindView();
        mActivity = this;

        orderEntity = getIntent().getExtras().getParcelable(OrderEntityHelper.DOMAIN_NAME);

        orderImageBase64.setImageBitmap(Utils.convertBase64ToImage(orderEntity.getImageBase64()));
        orderImageBase64.setContentDescription(getResources().getString(R.string.driver_image_cd));

        String orderNameText = orderEntity.getName();
        orderName.setText(orderNameText);
        orderName.setContentDescription(orderNameText);

        String customerNameText = orderEntity.getCustomer();
        customerName.setText("to " + customerNameText);
        customerName.setContentDescription("order to " + customerNameText);


        String orderDistanceKMText = orderEntity.getDistanceKM();
        orderDistanceKM.setText(orderDistanceKMText);
        this.orderDistanceKM.setContentDescription(mActivity.getString(R.string.order_distance_cd) + " " + orderDistanceKMText);





        //init loader
        this.getLoaderManager().restartLoader(LOADER_SEARCH_RESULTS, null, this);


        //delivered button
        delivered.setContentDescription(mActivity.getString(R.string.is_delivered));
        delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, com.example.android.capstone.ResultActivity.class);
                intent.putExtra(OrderEntityHelper.DOMAIN_NAME, orderEntity);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

        updateMap();

    }

    private void updateMap(){

        final Location myLocation = Utils.getLastKnownLocation(mActivity);

        if(myLocation!=null) {

            SupportMapFragment routeMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.route_map);
            routeMap.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(final GoogleMap googleMap) {
                    mGoogleMap = googleMap;

                    if (orderEntity.getLocationEntity() != null) {
                        myLatLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                    } else {
                        myLatLng = new LatLng(Double.valueOf(orderEntity.getLocationEntity().getLatitude()), Double.valueOf(orderEntity.getLocationEntity().getLongitude()));
                    }

                    toLatLng = new LatLng(Double.parseDouble(orderEntity.getLocationEntity().getLatitude()), Double.parseDouble(orderEntity.getLocationEntity().getLongitude()));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 14));

                    GoogleDirection.withServerKey(getResources().getString(R.string.google_maps_direction_key))
                            .from(myLatLng)
                            .to(toLatLng)
                            .transportMode(TransportMode.WALKING)
                                    //.avoid(AvoidType.FERRIES)
                                    //.avoid(AvoidType.HIGHWAYS)
                            .execute(mActivity);
                }
            });
        }
    }

    @Override
    public void onDirectionSuccess(final Direction direction) {

        if(Utils.checkMapPermission(mActivity)){

            if (direction.isOK()) {

                if (direction.isOK()) {

                    BitmapDescriptor fromMarkerIcon = BitmapDescriptorFactory.fromResource(R.drawable.marker_me);
                    mGoogleMap.addMarker(new MarkerOptions().position(myLatLng).icon(fromMarkerIcon));

                    BitmapDescriptor toMarkerIcon = BitmapDescriptorFactory.fromResource(R.drawable.marker_customer_2);
                    mGoogleMap.addMarker(new MarkerOptions().position(toLatLng).icon(toMarkerIcon));


                    for (int i = 0; i < direction.getRouteList().size(); i++) {

                        Route route = direction.getRouteList().get(i);

                        ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();

                        if(mPolyline!=null) {
                            mPolyline.remove();
                        }

                        mPolyline = mGoogleMap.addPolyline(DirectionConverter.createPolyline(mActivity, directionPositionList, 5, ContextCompat.getColor(mActivity, R.color.colorPrimaryTransparent)));

                        mGoogleMap.setMyLocationEnabled(true);

                    }


                }

            } else {
                // Do something
            }

        }


    }

    @Override
    public void onDirectionFailure(Throwable t) {

    }

    private void setDistanceKM(String distanceKM){
        orderDistanceKM.setText(distanceKM);
    }


    @Override
    public Loader<Cursor> onCreateLoader(final int id, Bundle bundle) {

        switch (id) {
            case LOADER_SEARCH_RESULTS:

                return new CursorLoader(RouteActivity.this,
                        ContentProvider.createUri(OrderEntity.class, null),
                        null, null, null, null
                );
        }

        return null;

    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case LOADER_SEARCH_RESULTS:

                cursor.moveToFirst();
                orderEntity = OrderEntityHelper.convertToOrderEntity(cursor);

                setDistanceKM(orderEntity.getDistanceKM());

                updateMap();

                break;
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {

    }
}
