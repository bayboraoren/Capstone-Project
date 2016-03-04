package com.example.android.capstone.components.location;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.example.android.capstone.R;
import com.example.android.capstone.util.Utils;
import com.example.android.firebase.entity.OrderEntity;
import com.example.android.firebase.entity.OrderEntityHelper;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by baybora on 3/4/16.
 */
public class DeliveryRouteLocationService extends Service {
    public static final String BROADCAST_ACTION = "Hello World";
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    public LocationManager locationManager;
    public MyLocationListener listener;
    public Location previousBestLocation = null;

    Intent intent;
    int counter = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        intent = new Intent(BROADCAST_ACTION);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new MyLocationListener();

        long minTime = 5 * 1000; // Minimum time interval for update in seconds, i.e. 5 seconds.
        long minDistance = 10; // Minimum distance change for update in meters, i.e. 10 meters.


        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, listener);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }


    /**
     * Checks whether two providers are the same
     */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }


    @Override
    public void onDestroy() {
        // handler.removeCallbacks(sendUpdatesToUI);
        super.onDestroy();
        Log.v("STOP_SERVICE", "DONE");
        locationManager.removeUpdates(listener);
    }

    /*public static Thread performOnBackgroundThread(final Runnable runnable) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } finally {

                }
            }
        };
        t.start();
        return t;
    }*/


    public class MyLocationListener implements LocationListener {

        public void onLocationChanged(final Location loc) {

            Log.i("**********************", "Location changed");

            if (isBetterLocation(loc, previousBestLocation)) {
                loc.getLatitude();
                loc.getLongitude();
                intent.putExtra("Latitude", loc.getLatitude());
                intent.putExtra("Longitude", loc.getLongitude());
                intent.putExtra("Provider", loc.getProvider());

                previousBestLocation = loc;

                updateLocation(loc);

                sendBroadcast(intent);


            }
        }


        private void updateLocation(Location myLocation) {

            List<OrderEntity> orderEntities = new Select().from(OrderEntity.class).execute();

            for (final OrderEntity orderEntity : orderEntities) {

                LatLng myLatLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                LatLng toLatLng = new LatLng(Double.valueOf(orderEntity.getLocationEntity().getLatitude()), Double.valueOf(orderEntity.getLocationEntity().getLongitude()));


                GoogleDirection.withServerKey(getResources().getString(R.string.google_maps_direction_key))
                        .from(myLatLng)
                        .to(toLatLng)
                        .transportMode(TransportMode.DRIVING)
                        .execute(new DirectionCallback() {

                            @Override
                            public void onDirectionSuccess(Direction direction) {

                                List<Route> routes = direction.getRouteList();
                                if (routes.size() > 0) {

                                    Route route = routes.get(0);

                                    if (route != null) {

                                        String distanceKM = route.getLegList().get(0).getDistance().getText();
                                        orderEntity.setDistanceKM(distanceKM);

                                    } else {

                                        String what = getResources().getString(R.string.what);
                                        orderEntity.setDistanceKM(what);

                                    }

                                    orderEntity.save();

                                    //Distance KM changed
                                    OrderEntity selectedOrderEntity = OrderEntityHelper.getSelectedOrder();

                                    if(null!=selectedOrderEntity && selectedOrderEntity.get_id()==orderEntity.getId()) {

                                        OrderEntityHelper.updateSelectedOrderDistanceKM(orderEntity.getDistanceKM());
                                        Utils.updateWidget(getBaseContext());

                                    }



                                }

                            }

                            @Override
                            public void onDirectionFailure(Throwable t) {

                            }
                        });


            }
        }



        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT).show();
        }


        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
        }


        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

    }
}