package com.example.android.util.domain;

import java.util.List;

/**
 * Created by baybora on 3/2/16.
 */
public class RestaurantsDomain extends LocationDomain{

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
}
