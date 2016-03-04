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
@Table(name = "Orders",id = BaseColumns._ID)
public class OrderEntity extends Model implements Parcelable {

    @Column
    private String name;
    @Column(unique = true)
    private String customer;
    @Column
    private String imageBase64;
    @Column
    private String distanceKM;
    @Column(name="location")
    private LocationEntity locationEntity;

    /**like dto not depends on table
     * it is used for selected Order for ui
    */
    private long _id;

    private transient long orderStartDeliverTime;

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

    public String getDistanceKM() {
        return distanceKM;
    }

    public void setDistanceKM(String distanceKM) {
        this.distanceKM = distanceKM;
    }

    public LocationEntity getLocationEntity() {
        return locationEntity;
    }

    public void setLocationEntity(LocationEntity locationEntity) {
        this.locationEntity = locationEntity;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public List<DriverEntity> drivers(){
        return getMany(DriverEntity.class,"OrderEntity");
    }

    public long getOrderStartDeliverTime() {
        return orderStartDeliverTime;
    }

    public void setOrderStartDeliverTime(long orderStartDeliverTime) {
        this.orderStartDeliverTime = orderStartDeliverTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.customer);
        dest.writeString(this.imageBase64);
        dest.writeString(this.distanceKM);
        dest.writeParcelable(this.locationEntity, flags);
        dest.writeLong(this.orderStartDeliverTime);
        dest.writeLong(this._id);
    }

    public OrderEntity() {
    }

    protected OrderEntity(Parcel in) {
        this.name = in.readString();
        this.customer = in.readString();
        this.imageBase64 = in.readString();
        this.distanceKM = in.readString();
        this.locationEntity = in.readParcelable(LocationEntity.class.getClassLoader());
        this.orderStartDeliverTime = in.readLong();
        this._id = in.readLong();
    }

    public static final Parcelable.Creator<OrderEntity> CREATOR = new Parcelable.Creator<OrderEntity>() {
        public OrderEntity createFromParcel(Parcel source) {
            return new OrderEntity(source);
        }

        public OrderEntity[] newArray(int size) {
            return new OrderEntity[size];
        }
    };
}
