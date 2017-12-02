package com.example.aupke.fridaybar;

import android.location.Location;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Aupke on 30-11-2017.
 */

@IgnoreExtraProperties
public class Offer {
    private String distanceToLocation;
    private String title;
    private String description;

    public Offer(String distanceToLocation, String title, String description) {
        this.distanceToLocation = distanceToLocation;
        this.title = title;
        this.description = description;
    }
    public Offer(){

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
}
