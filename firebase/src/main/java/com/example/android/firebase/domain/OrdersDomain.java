package com.example.android.firebase.domain;

import android.os.Parcel;

import java.util.List;

/**
 * Created by baybora on 3/2/16.
 */
public class OrdersDomain extends LocationDomain {


    private String name;
    private String customer;
    private String imageBase64;
    private List<DriversDomain> driversDomains;
    private String distanceKM;

    //when order selected and start to deliver
    private long orderStartDeliverTime;

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

    public String getDistanceKM() {
        return distanceKM;
    }

    public void setDistanceKM(String distanceKM) {
        this.distanceKM = distanceKM;
    }

    public long getOrderStartDeliverTime() {
        return orderStartDeliverTime;
    }

    public void setOrderStartDeliverTime(long orderStartDeliverTime) {
        this.orderStartDeliverTime = orderStartDeliverTime;
    }

    public OrdersDomain() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.name);
        dest.writeString(this.customer);
        dest.writeString(this.imageBase64);
        dest.writeTypedList(driversDomains);
        dest.writeString(this.distanceKM);
        dest.writeLong(this.orderStartDeliverTime);
        dest.writeString(this.getLongitude());
        dest.writeString(this.getLatitude());
    }

    protected OrdersDomain(Parcel in) {
        super(in);
        this.name = in.readString();
        this.customer = in.readString();
        this.imageBase64 = in.readString();
        this.driversDomains = in.createTypedArrayList(DriversDomain.CREATOR);
        this.distanceKM = in.readString();
        this.orderStartDeliverTime = in.readLong();
        this.setLongitude(in.readString());
        this.setLatitude(in.readString());

    }

    public static final Creator<OrdersDomain> CREATOR = new Creator<OrdersDomain>() {
        public OrdersDomain createFromParcel(Parcel source) {
            return new OrdersDomain(source);
        }

        public OrdersDomain[] newArray(int size) {
            return new OrdersDomain[size];
        }
    };
}
