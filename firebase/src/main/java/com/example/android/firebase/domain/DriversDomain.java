package com.example.android.firebase.domain;

import android.os.Parcel;

import java.util.List;

/**
 * Created by baybora on 3/2/16.
 */
public class DriversDomain extends LocationDomain {

    public static final String NAME="name";
    public static final String SURNAME="surname";
    public static final String EMAIL="email";
    public static final String IMAGE_BASE_64="imagebase64";

    private String name;
    private String surname;
    private String email;
    private String imageBase64;
    private List<OrdersDomain> ordersDomain;
    private RestaurantsDomain restaurantsDomain;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public List<OrdersDomain> getOrdersDomain() {
        return ordersDomain;
    }

    public void setOrdersDomain(List<OrdersDomain> ordersDomain) {
        this.ordersDomain = ordersDomain;
    }

    public RestaurantsDomain getRestaurantsDomain() {
        return restaurantsDomain;
    }

    public void setRestaurantsDomain(RestaurantsDomain restaurantsDomain) {
        this.restaurantsDomain = restaurantsDomain;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.name);
        dest.writeString(this.surname);
        dest.writeString(this.email);
        dest.writeString(this.imageBase64);
        dest.writeTypedList(ordersDomain);
        dest.writeParcelable(this.restaurantsDomain, 0);
    }

    public DriversDomain() {
    }

    protected DriversDomain(Parcel in) {
        super(in);
        this.name = in.readString();
        this.surname = in.readString();
        this.email = in.readString();
        this.imageBase64 = in.readString();
        this.ordersDomain = in.createTypedArrayList(OrdersDomain.CREATOR);
        this.restaurantsDomain = in.readParcelable(RestaurantsDomain.class.getClassLoader());
    }

    public static final Creator<DriversDomain> CREATOR = new Creator<DriversDomain>() {
        public DriversDomain createFromParcel(Parcel source) {
            return new DriversDomain(source);
        }

        public DriversDomain[] newArray(int size) {
            return new DriversDomain[size];
        }
    };
}

