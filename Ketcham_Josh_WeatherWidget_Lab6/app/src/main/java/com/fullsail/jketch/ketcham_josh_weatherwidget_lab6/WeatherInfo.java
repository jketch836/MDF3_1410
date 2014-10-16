package com.fullsail.jketch.ketcham_josh_weatherwidget_lab6;

import java.io.Serializable;


public class WeatherInfo implements Serializable {

    String conditions;
    String highLow;
    String temp;
    String wBitmap;

    public WeatherInfo (String theConditions, String theHighLow, String theTemp, String bitmap) {

        conditions = theConditions;
        highLow = theHighLow;
        temp = theTemp;
        wBitmap = bitmap;

    }

    public String getConditions() {

        return conditions;

    }

    public String getHighLow() {

        return highLow;

    }

    public String getTemp() {

        return temp;

    }

    public String getBitmap() {

        return wBitmap;

    }

}
