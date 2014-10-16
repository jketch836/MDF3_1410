package com.fullsail.jketch.ketcham_josh_collectionwidget_lab7.WidgetPackage;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by jketch on 10/16/14.
 */
public class CollectionRemoteService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new CollectionFactory(getApplicationContext());
    }

}
