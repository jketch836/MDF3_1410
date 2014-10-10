package com.fullsail.jketch.ketcham_josh_lab5;

import java.io.Serializable;

/**
 * Created by jketch on 10/9/14.
 */
public class MapInfo implements Serializable {

    String title;
    String theSnippet;
    String GeoData;
    double latitude;
    double longitude;


    public MapInfo(String Title, String shortDesc, String data, double lat, double lng) {

        title = Title;
        theSnippet = shortDesc;
        GeoData = data;
        latitude = lat;
        longitude = lng;

    }

    public String getTitle() {

        return title;
    }


    public String getSnippet() {

        return theSnippet;
    }

    public String getData() {

        return GeoData;
    }

    public double getLat() {

        return latitude;
    }

    public double getLong() {

        return longitude;
    }

}
