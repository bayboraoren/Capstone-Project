package com.example.android.capstone;

import com.activeandroid.ActiveAndroid;

/**
 * Created by baybora on 3/3/16.
 */
public class Application extends com.activeandroid.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

}
