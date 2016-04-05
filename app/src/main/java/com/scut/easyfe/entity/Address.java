package com.scut.easyfe.entity;

/**
 * 地址类
 * Created by jay on 16/4/5.
 */
public class Address extends BaseEntity{
    private String address = "";
    private float latitude = 0f;
    private float longitude = 0f;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
