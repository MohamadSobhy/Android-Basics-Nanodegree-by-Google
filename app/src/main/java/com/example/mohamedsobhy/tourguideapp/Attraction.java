package com.example.mohamedsobhy.tourguideapp;

import android.location.Location;

/**
 * Created by Mohamed Sobhy on 18/01/2018.
 */

public class Attraction {

    private int mAttractionImageResource;
    private String mAttractionName;
    private String mAttractionDescription;
    private Location mAttractionLocation;
    private String mAttractionAddress;

    public Attraction(int attractionImageResource, String attractionName , String attractionDescription
            , String attractionLatitude , String attractionLongitude , String attractionAddress){
        mAttractionImageResource = attractionImageResource;
        mAttractionName = attractionName;
        mAttractionDescription = attractionDescription;

        mAttractionLocation = new Location(mAttractionName);
        try {
            mAttractionLocation.setLatitude(Double.parseDouble(attractionLatitude));
            mAttractionLocation.setLongitude(Double.parseDouble(attractionLongitude));
        }catch (Exception e){
            mAttractionLocation.setLatitude(-1);
            mAttractionLocation.setLongitude(-1);
        }

        mAttractionAddress = attractionAddress;
    }

    public int getmAttractionImageResource() {
        return mAttractionImageResource;
    }

    public String getAttractionName() {
        return mAttractionName;
    }

    public String getAttractionDescription() {
        return mAttractionDescription;
    }

    public Location getmAttractionLocation(){
        return mAttractionLocation;
    }

    public String getAttractionAddress() {
        return mAttractionAddress;
    }
}
