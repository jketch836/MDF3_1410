package com.fullsail.jketch.ketcham_josh_collectionwidget_lab7;

import java.io.Serializable;

/**
 * Created by jketch on 10/16/14.
 */
public class PersonInfoClass implements Serializable {

    String fName;
    String lName;
    String age;

    public PersonInfoClass (String first, String last, String old) {

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

    @Override
    public String toString (){

        return fName + " " + lName;

    }

}
