package com.example.android.firebase.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by baybora on 3/3/16.
 */
@Table(name = "Locations",id = BaseColumns._ID)
public class LocationEntity extends Model implements Parcelable {

    @Column
    private String longitude;
    @Column
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

    public List<DriverEntity> drivers() {
        return getMany(DriverEntity.class, "Location");
    }

    public List<OrderEntity> orders() {
        return getMany(OrderEntity.class, "Location");
    }

    public List<RestaurantEntity> restaurants() {
        return getMany(RestaurantEntity.class, "Location");
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

    public LocationEntity() {
    }

    protected LocationEntity(Parcel in) {
        this.longitude = in.readString();
        this.latitude = in.readString();
    }

    public static final Parcelable.Creator<LocationEntity> CREATOR = new Parcelable.Creator<LocationEntity>() {
        public LocationEntity createFromParcel(Parcel source) {
            return new LocationEntity(source);
        }

        public LocationEntity[] newArray(int size) {
            return new LocationEntity[size];
        }
    };
}
