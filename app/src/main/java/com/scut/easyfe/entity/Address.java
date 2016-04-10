package com.scut.easyfe.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 地址类
 * Created by jay on 16/4/5.
 */
public class Address extends BaseEntity{
    private String address = "";
    private String city = "";
    private double latitude = -1d;
    private double longitude = -1d;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public JSONObject getAddressJson(){
        JSONObject addressJson = new JSONObject();
        try {
            addressJson.put("address", address);
            addressJson.put("city", city);
            addressJson.put("latitude", latitude);
            addressJson.put("longitude", longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return addressJson;
    }
}
