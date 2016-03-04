package com.example.android.capstone.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.widget.Toast;

import com.example.android.capstone.R;
import com.example.android.firebase.entity.LocationEntity;
import com.example.android.firebase.entity.OrderEntity;
import com.example.android.firebase.entity.OrderEntityHelper;

import java.util.List;

/**
 * Created by baybora on 3/2/16.
 */
public class Utils {

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
            Toast.makeText(context, context.getResources().getString(R.string.map_permission_message), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public static Location getLastKnownLocation(Context context) {

        Location bestLocation = null;
        LocationManager mLocationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);


        if (Utils.checkMapPermission(context)) {

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


    private static OrderEntity convertToOrderEntity(Cursor cursor){

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setName(cursor.getString(cursor.getColumnIndex(OrderEntityHelper.NAME)));
        orderEntity.setImageBase64(cursor.getString(cursor.getColumnIndex(OrderEntityHelper.IMAGE_BASE_64)));
        orderEntity.setDistanceKM(cursor.getString(cursor.getColumnIndex(OrderEntityHelper.DISTANCE_KM)));
        orderEntity.setCustomer(cursor.getString(cursor.getColumnIndex(OrderEntityHelper.CUSTOMER)));

        //location entity
        long locationEntityId = cursor.getLong(cursor.getColumnIndex(OrderEntityHelper.LOCATION));
        LocationEntity locationEntity = LocationEntity.load(LocationEntity.class, locationEntityId);
        orderEntity.setLocationEntity(locationEntity);

        return orderEntity;
    }

}
