package com.example.android.capstone.domain;

import java.util.List;

/**
 * Created by baybora on 3/2/16.
 */
public class DriversDomain extends LocationDomain{

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
}
