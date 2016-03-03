package com.example.android.capstone;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.android.capstone.util.Utils;
import com.example.android.firebase.FirebaseUtil;
import com.example.android.firebase.domain.OrdersDomain;
import com.firebase.client.Firebase;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by baybora on 3/2/16.
 */
public class RouteActivity extends com.example.android.capstone.BaseActivity implements DirectionCallback {

    @Bind(R.id.order_imagebase64)
    ImageView orderImageBase64;

    @Bind(R.id.order_name)
    TextView orderName;

    @Bind(R.id.customer_name)
    TextView customerName;

    @Bind(R.id.order_distance_km)
    TextView orderDistanceKM;

    private RouteActivity mActivity;


    //map
    private GoogleMap mGoogleMap;
    private LatLng camera = new LatLng(35.1773909, 136.9471357);
    private LatLng origin = new LatLng(35.1766982, 136.9413508);
    private LatLng destination = new LatLng(35.1800441, 136.9532567);
    private String[] colors = {"#7fff7272", "#7f31c7c5", "#7fff8a00"};

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

        final OrdersDomain ordersDomain = getIntent().getExtras().getParcelable(OrdersDomain.DOMAIN_NAME);
        orderImageBase64.setImageBitmap(Utils.convertImageToBase64(ordersDomain.getImageBase64()));
        orderName.setText(ordersDomain.getName());
        customerName.setText("to " + ordersDomain.getCustomer());
        orderDistanceKM.setText("");

        SupportMapFragment routeMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.route_map);
        routeMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                mGoogleMap = googleMap;
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(camera, 15));
                GoogleDirection.withServerKey(getResources().getString(R.string.google_maps_direction_key))
                        .from(origin)
                        .to(destination)
                        .transportMode(TransportMode.WALKING)
                                //.avoid(AvoidType.FERRIES)
                                //.avoid(AvoidType.HIGHWAYS)
                        .execute(mActivity);
            }
        });

    }

    @Override
    public void onDirectionSuccess(final Direction direction) {

        if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getBaseContext(), "Harita kullanmak için telefondan izin vermeniz gerekmektedir.", Toast.LENGTH_SHORT).show();
        } else {

            if (direction.isOK()) {

                if (direction.isOK()) {

                    mGoogleMap.addMarker(new MarkerOptions().position(origin));
                    mGoogleMap.addMarker(new MarkerOptions().position(destination));


                    for (int i = 0; i < direction.getRouteList().size(); i++) {
                        Route route = direction.getRouteList().get(i);
                        String color = colors[i % colors.length];
                        ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
                        mGoogleMap.addPolyline(DirectionConverter.createPolyline(mActivity, directionPositionList, 5, Color.parseColor(color)));
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

}
