package com.fullsail.jketch.ketcham_josh_la2_recipewidget.Widget3x1;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by jketch on 10/23/14.
 */
public class Widget31Service extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new Widget31Factory(getApplicationContext());
    }
}
