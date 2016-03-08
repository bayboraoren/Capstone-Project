package com.example.android.capstone.util;

import android.Manifest;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.android.capstone.R;
import com.example.android.capstone.components.widget.DeliveryRouteAppWidgetProvider;

import java.util.List;

/**
 * Created by baybora on 3/2/16.
 */
public class Utils {

    public static final String LOG_TAG = Utils.class.getSimpleName();


    public static String getString(Context context, int name) {
        return context.getResources().getString(name);
    }

    public static Bitmap convertBase64ToImage(String imageBase64) {
        byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public static boolean checkMapPermission(Context context) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, context.getResources().getString(R.string.map_permission_message), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public static boolean checkLocationServiceEnabled(Context context, LocationManager locationManager){
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {

            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        } catch(Exception exception) {
            Log.e(LOG_TAG,exception.getMessage(),exception);
        }

        if(!gps_enabled && !network_enabled) {
            Toast.makeText(context, context.getResources().getString(R.string.map_permission_message), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public static Location getLastKnownLocation(Context context) {

        Location bestLocation = null;
        LocationManager mLocationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);


        if (Utils.checkMapPermission(context) && Utils.checkLocationServiceEnabled(context,mLocationManager)) {

            List<String> providers = mLocationManager.getProviders(true);

            for (java.lang.String provider : providers) {
                Location l = mLocationManager.getLastKnownLocation(provider);

                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    bestLocation = l;
                }
            }
            if (bestLocation == null) {
                return null;
            }
        }
        return bestLocation;
    }


    public static void updateWidget(Context context) {
        Intent intent = new Intent(context, DeliveryRouteAppWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int ids[] = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, DeliveryRouteAppWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(intent);
    }

    public static Bitmap convertDrawabletoBitmap(Context context, int drawable){
        return BitmapFactory.decodeResource(context.getResources(),
                drawable);
    }




}
