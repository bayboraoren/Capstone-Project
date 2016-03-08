package com.example.android.capstone;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.capstone.util.Utils;
import com.example.android.firebase.entity.OrderEntity;
import com.example.android.firebase.entity.OrderEntityHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    @Bind(R.id.order_start_deliver_time)
    TextView orderStartDeliverTime;


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

        final OrderEntity orderEntity = getIntent().getExtras().getParcelable(OrderEntityHelper.DOMAIN_NAME);

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


        //for calculate deliver time
        long diffInMillisec = new Date().getTime() - orderEntity.getOrderStartDeliverTime();
        long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMillisec);
        diffInSec/= 60;
        long minutes =diffInSec % 60;
        StringBuilder deliverTimeText = new StringBuilder();
        deliverTimeText.append(getResources().getString(R.string.in)).append(" ").append(minutes).append(" ").append(getResources().getString(R.string.minutes));
        orderStartDeliverTime.setText(deliverTimeText.toString());


        //about map
        SupportMapFragment routeMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.route_map);
        routeMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                mGoogleMap = googleMap;

                //location entity
                LatLng toLatLng = new LatLng(Double.parseDouble(orderEntity.getLocationEntity().getLatitude()), Double.parseDouble(orderEntity.getLocationEntity().getLongitude()));


                BitmapDescriptor fromMarkerIcon = BitmapDescriptorFactory.fromResource(R.drawable.marker_me);
                mGoogleMap.addMarker(new MarkerOptions().position(toLatLng).icon(fromMarkerIcon));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(toLatLng, 14));

            }
        });


    }


}
