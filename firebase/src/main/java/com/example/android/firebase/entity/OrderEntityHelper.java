package com.example.android.firebase.entity;

import android.database.Cursor;

/**
 * Created by baybora on 3/3/16.
 */
public class OrderEntityHelper extends LocationEntityHelper{

    public static final String DOMAIN_NAME = "orders";
    public static final String ID="_id";
    public static final String NAME="name";
    public static final String CUSTOMER="customer";
    public static final String IMAGE_BASE_64="imageBase64";
    public static final String DISTANCE_KM="distanceKM";

    public static OrderEntity mSelectedOrderEntity;

    public static OrderEntity convertToOrderEntity(Cursor cursor){

        OrderEntity orderEntity = new OrderEntity();

        orderEntity.set_id(cursor.getLong(cursor.getColumnIndex(OrderEntityHelper.ID)));
        orderEntity.setName(cursor.getString(cursor.getColumnIndex(OrderEntityHelper.NAME)));
        orderEntity.setImageBase64(cursor.getString(cursor.getColumnIndex(OrderEntityHelper.IMAGE_BASE_64)));
        orderEntity.setDistanceKM(cursor.getString(cursor.getColumnIndex(OrderEntityHelper.DISTANCE_KM)));
        orderEntity.setCustomer(cursor.getString(cursor.getColumnIndex(OrderEntityHelper.CUSTOMER)));

        //location entity
        long locationEntityId = cursor.getLong(cursor.getColumnIndex(OrderEntityHelper.LOCATION));
        LocationEntity locationEntity = LocationEntity.load(LocationEntity.class, locationEntityId);
        orderEntity.setLocationEntity(locationEntity);

        return orderEntity;
    }

    public static void setSelectedOrder(OrderEntity orderEntity){
        mSelectedOrderEntity = orderEntity;
    }

    public static void updateSelectedOrderDistanceKM(String distanceKM){
        mSelectedOrderEntity.setDistanceKM(distanceKM);
    }

    public static OrderEntity getSelectedOrder(){
        return mSelectedOrderEntity;
    }

}
