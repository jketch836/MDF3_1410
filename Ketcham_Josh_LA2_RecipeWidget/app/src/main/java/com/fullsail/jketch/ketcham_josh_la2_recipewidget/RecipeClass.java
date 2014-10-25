package com.fullsail.jketch.ketcham_josh_la2_recipewidget;

import java.io.Serializable;

/**
 * Created by jketch on 10/19/14.
 */
public class RecipeClass implements Serializable {

    public static final long SerialVersionID = 987654321L;
    String name, image, rating;
    StringBuilder ingrediants;

    public RecipeClass(String thename, String theimage, StringBuilder theingrediants, String therating) {

        name = thename;
        image = theimage;
        ingrediants = theingrediants;
        rating = therating;

    }

    public String getName() {

        return name;

    }

    public String getImage() {

        return image;

    }

    public StringBuilder getIngrediants() {

        return ingrediants;

    }

    public String getRating() {

        return rating;

    }

    @Override
    public String toString() {
        return name;
    }
}
