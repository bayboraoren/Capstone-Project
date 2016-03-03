package com.example.android.capstone.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by baybora on 3/3/16.
 */
@Table(name = "Locations")
public class LocationEntity extends Model {

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


}
