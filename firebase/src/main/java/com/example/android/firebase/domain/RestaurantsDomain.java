package com.example.android.firebase.domain;

import android.os.Parcel;

import java.util.List;

/**
 * Created by baybora on 3/2/16.
 */
public class RestaurantsDomain extends LocationDomain {


    public static final String NAME="name";

    private String name;
    private List<DriversDomain> driversDomains;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DriversDomain> getDriversDomains() {
        return driversDomains;
    }

    public void setDriversDomains(List<DriversDomain> driversDomains) {
        this.driversDomains = driversDomains;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.name);
        dest.writeTypedList(driversDomains);
    }

    public RestaurantsDomain() {
    }

    protected RestaurantsDomain(Parcel in) {
        super(in);
        this.name = in.readString();
        this.driversDomains = in.createTypedArrayList(DriversDomain.CREATOR);
    }

    public static final Creator<RestaurantsDomain> CREATOR = new Creator<RestaurantsDomain>() {
        public RestaurantsDomain createFromParcel(Parcel source) {
            return new RestaurantsDomain(source);
        }

        public RestaurantsDomain[] newArray(int size) {
            return new RestaurantsDomain[size];
        }
    };
}
