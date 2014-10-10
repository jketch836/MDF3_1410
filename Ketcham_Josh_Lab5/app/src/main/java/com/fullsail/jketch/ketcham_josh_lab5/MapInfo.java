package com.fullsail.jketch.ketcham_josh_lab5;

import java.io.File;

/**
 * Created by jketch on 10/9/14.
 */
public class MapInfo {

    String title;
    String theSnippet;
    File GeoData;
    double latitude;
    double longitude;


    public MapInfo(String Title, String shortDesc, File data, double lat, double lng) {

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

    public File getData() {

        return GeoData;
    }

    public double getLat() {

        return latitude;
    }

    public double getLong() {

        return longitude;
    }

}
