package com.paypal.first.paypalsingaporeapp;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by gajg on 2/6/2015.
 */
public class Event implements Serializable{

    String date = "";
    String name = "";
    String place = "";
    LatLng latLng = null;

    public Event(){

    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
