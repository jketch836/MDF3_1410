package com.fullsail.jketch.ketcham_josh_la2_recipewidget.Widget3x2;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by jketch on 10/23/14.
 */
public class Widget32Service extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new Widget32Factory(getApplicationContext());
    }


}
