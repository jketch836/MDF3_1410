package com.fullsail.jketch.ketcham_josh_lab1_broadcastreceiver_project;

import java.io.Serializable;

/**
 * Created by jketch on 9/30/14.
 */
public class FormData implements Serializable {

    String fName;
    String lName;
    String age;

    public FormData (String first, String last, String old) {

        fName = first;
        lName = last;
        age = old;

    }

    public String getfName (){

        return fName;
    }


    public String getlName (){

        return lName;
    }

    public String getAge (){

        return age;
    }
}
