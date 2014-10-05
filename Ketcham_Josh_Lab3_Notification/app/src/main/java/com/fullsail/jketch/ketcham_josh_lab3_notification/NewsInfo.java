package com.fullsail.jketch.ketcham_josh_lab3_notification;

import java.io.Serializable;

public class NewsInfo implements Serializable {

    String theSummary;
    String theTitle;
    String theURL;

    public NewsInfo(String summary, String title, String url) {

        theSummary = summary;
        theTitle = title;
        theURL = url;

    }

    public String getSummary() {

        return theSummary;
    }

    public String getTitle() {

        return theTitle;
    }

    public String getURL() {

        return theURL;
    }

}
