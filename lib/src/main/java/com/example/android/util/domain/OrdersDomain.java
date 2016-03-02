package com.example.android.util.domain;

import java.util.List;

/**
 * Created by baybora on 3/2/16.
 */
public class OrdersDomain extends LocationDomain{

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
}
