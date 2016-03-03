package com.example.android.capstone;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.capstone.util.Utils;
import com.example.android.firebase.domain.OrdersDomain;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.Bind;

/**
 * Created by baybora on 3/3/16.
 */
public class ResultActivity extends com.example.android.capstone.BaseActivity{

    @Bind(R.id.order_imagebase64)
    ImageView orderImageBase64;

    @Bind(R.id.order_name)
    TextView orderName;

    @Bind(R.id.customer_name)
    TextView customerName;

    @Bind(R.id.order_distance_km)
    TextView orderDistanceKM;

    private GoogleMap mGoogleMap;
    private ResultActivity mActivity;

    public ResultActivity(){
        super(ResultActivity.class.getSimpleName(),"RESULT");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initResultActivity();
    }

    public void initResultActivity(){
        initLayout(R.layout.activity_result, LAYOUT_TITLE, true, ResultActivity.LAYOUT_TITLE);
        initBindView();
        mActivity = this;

        final OrdersDomain ordersDomain = getIntent().getExtras().getParcelable(OrdersDomain.DOMAIN_NAME);
        orderImageBase64.setImageBitmap(Utils.convertImageToBase64(ordersDomain.getImageBase64()));
        orderName.setText(ordersDomain.getName());
        customerName.setText("to " + ordersDomain.getCustomer());
        orderDistanceKM.setText(ordersDomain.getDistanceKM());

        //about map
        SupportMapFragment routeMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.route_map);
        routeMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                mGoogleMap = googleMap;
                LatLng toLatLng = new LatLng(Double.parseDouble(ordersDomain.getLatitude()), Double.parseDouble(ordersDomain.getLongitude()));


                BitmapDescriptor fromMarkerIcon = BitmapDescriptorFactory.fromResource(R.drawable.marker_me);
                mGoogleMap.addMarker(new MarkerOptions().position(toLatLng).icon(fromMarkerIcon));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(toLatLng, 14));

            }
        });


    }


}
