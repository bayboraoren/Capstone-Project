package com.example.android.firebase.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by baybora on 3/2/16.
 */
public class LocationDomain implements Parcelable {

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


    public LocationDomain() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.longitude);
        dest.writeString(this.latitude);
    }

    protected LocationDomain(Parcel in) {
        this.longitude = in.readString();
        this.latitude = in.readString();
    }

    public static final Creator<LocationDomain> CREATOR = new Creator<LocationDomain>() {
        public LocationDomain createFromParcel(Parcel source) {
            return new LocationDomain(source);
        }

        public LocationDomain[] newArray(int size) {
            return new LocationDomain[size];
        }
    };
}
