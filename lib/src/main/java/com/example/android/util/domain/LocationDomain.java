package com.example.android.util.domain;

/**
 * Created by baybora on 3/2/16.
 */
public class LocationDomain {

    public static final String LONGITUDE="lon";
    public static final String LATITUDE="lat";

    private String longitude;
    private String latitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
