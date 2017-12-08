package com.example.aupke.fridaybar;

import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by Aupke on 30-11-2017.
 */

@IgnoreExtraProperties
public class Offer implements Serializable, Comparable<Offer> {
    private String bar;
    private int distanceToLocation;
    private String title;
    private String description;
    private String date;
    private double lat;
    private double lng;
    private String type;
    private boolean isChecked;

    public Offer(int distanceToLocation, String title, String description) {
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

    public Offer(String bar, String title, String description, String type, String date, double lat, double lng, boolean isChecked) {
        this.type = type;
        this.bar = bar;
        this.title = title;
        this.description = description;
        this.date = date;
        this.lat = lat;
        this.lng = lng;
        this.isChecked = isChecked;
    }

    public int getDistanceToLocation() {
        return distanceToLocation;
    }

    public void setDistanceToLocation(int distanceToLocation) {
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

    @Override
    public int compareTo(@NonNull Offer o) {

        return distanceToLocation-o.distanceToLocation;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Offer offer = (Offer) o;

        if (distanceToLocation != offer.distanceToLocation) return false;
        if (Double.compare(offer.lat, lat) != 0) return false;
        if (Double.compare(offer.lng, lng) != 0) return false;
        if (isChecked != offer.isChecked) return false;
        if (bar != null ? !bar.equals(offer.bar) : offer.bar != null) return false;
        if (title != null ? !title.equals(offer.title) : offer.title != null) return false;
        if (description != null ? !description.equals(offer.description) : offer.description != null)
            return false;
        if (date != null ? !date.equals(offer.date) : offer.date != null) return false;
        return type != null ? type.equals(offer.type) : offer.type == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = bar != null ? bar.hashCode() : 0;
        result = 31 * result + distanceToLocation;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        temp = Double.doubleToLongBits(lat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lng);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (isChecked ? 1 : 0);
        return result;
    }
}
