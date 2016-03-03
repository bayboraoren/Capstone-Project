package com.example.android.capstone.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.widget.Toast;

import java.util.List;

/**
 * Created by baybora on 3/2/16.
 */
public class Utils {

    public static String getString(Context context, int name){
        return context.getResources().getString(name);
    }

    public static Bitmap convertImageToBase64(String imageBase64){
        byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public static Location getLastKnownLocation(Context context) {

        Location bestLocation = null;
        LocationManager mLocationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(context, "Harita kullanmak için telefondan izin vermeniz gerekmektedir.", Toast.LENGTH_SHORT).show();

        } else {


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



}
