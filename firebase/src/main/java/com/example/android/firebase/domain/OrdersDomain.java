package com.example.android.firebase.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baybora on 3/2/16.
 */
public class OrdersDomain extends LocationDomain implements android.os.Parcelable {

    public static final String NAME="name";
    public static final String CUSTOMER="customer";
    public static final String IMAGE_BASE_64="imagebase64";


    private String name;
    private String customer;
    private String imageBase64;
    private List<DriversDomain> driversDomains;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
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
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.customer);
        dest.writeString(this.imageBase64);
        dest.writeList(this.driversDomains);
    }

    public OrdersDomain() {
    }

    protected OrdersDomain(android.os.Parcel in) {
        this.name = in.readString();
        this.customer = in.readString();
        this.imageBase64 = in.readString();
        this.driversDomains = new ArrayList<DriversDomain>();
        in.readList(this.driversDomains, List.class.getClassLoader());
    }

    public static final android.os.Parcelable.Creator<OrdersDomain> CREATOR = new android.os.Parcelable.Creator<OrdersDomain>() {
        public OrdersDomain createFromParcel(android.os.Parcel source) {
            return new OrdersDomain(source);
        }

        public OrdersDomain[] newArray(int size) {
            return new OrdersDomain[size];
        }
    };
}
