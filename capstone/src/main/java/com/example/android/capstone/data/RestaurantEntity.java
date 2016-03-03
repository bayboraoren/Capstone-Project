package com.example.android.capstone.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by baybora on 3/3/16.
 */
@Table(name = "Restaurants")
public class RestaurantEntity extends Model {

    @Column
    private String name;
    @Column
    private List<DriverEntity> driverEntities;
    @Column
    private LocationEntity locationEntity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DriverEntity> getDriverEntities() {
        return driverEntities;
    }

    public void setDriverEntities(List<DriverEntity> driverEntities) {
        this.driverEntities = driverEntities;
    }

    public LocationEntity getLocationEntity() {
        return locationEntity;
    }

    public void setLocationEntity(LocationEntity locationEntity) {
        this.locationEntity = locationEntity;
    }
}


