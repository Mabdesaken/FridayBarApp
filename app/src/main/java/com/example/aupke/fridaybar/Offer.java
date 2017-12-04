package com.example.aupke.fridaybar;

import android.location.Location;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by Aupke on 30-11-2017.
 */

@IgnoreExtraProperties
public class Offer implements Serializable {
    private String bar;
    private String distanceToLocation;
    private String title;
    private String description;
    private String date;
    private double lat;
    private double lng;
    private String type;

    public Offer(String distanceToLocation, String title, String description) {
        this.distanceToLocation = distanceToLocation;
        this.title = title;
        this.description = description;
    }

    public Offer(String bar, String date, String title, String description){
        this.date = date;
        this.title = title;
        this.description = description;
        this.bar = bar;
    }

    public Offer(){

    }

    public Offer(String bar, String title, String description, String type, String date, double lat, double lng) {
        this.type = type;
        this.bar = bar;
        this.title = title;
        this.description = description;
        this.date = date;
        this.lat = lat;
        this.lng = lng;
    }

    public String getDistanceToLocation() {
        return distanceToLocation;
    }

    public void setDistanceToLocation(String distanceToLocation) {
        this.distanceToLocation = distanceToLocation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
