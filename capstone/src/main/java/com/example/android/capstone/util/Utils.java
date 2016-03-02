package com.example.android.capstone.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

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

}
