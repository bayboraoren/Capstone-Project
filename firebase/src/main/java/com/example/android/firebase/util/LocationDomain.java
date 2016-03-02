package com.example.android.firebase.util;

/**
 * Created by baybora on 3/2/16.
 */
public class LocationDomain implements android.os.Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeString(this.longitude);
        dest.writeString(this.latitude);
    }

    public LocationDomain() {
    }

    protected LocationDomain(android.os.Parcel in) {
        this.longitude = in.readString();
        this.latitude = in.readString();
    }

}
