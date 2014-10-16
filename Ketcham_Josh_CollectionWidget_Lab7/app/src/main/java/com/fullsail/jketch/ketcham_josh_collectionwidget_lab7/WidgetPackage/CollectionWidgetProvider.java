package com.fullsail.jketch.ketcham_josh_collectionwidget_lab7.WidgetPackage;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.fullsail.jketch.ketcham_josh_collectionwidget_lab7.FormActivity;
import com.fullsail.jketch.ketcham_josh_collectionwidget_lab7.R;
import com.fullsail.jketch.ketcham_josh_collectionwidget_lab7.ViewActivity;

/**
 * Created by jketch on 10/16/14.
 */
public class CollectionWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i = 0; i < appWidgetIds.length; i++) {

            int theWidgetId = appWidgetIds[i];


            RemoteViews rvs = new RemoteViews(context.getPackageName(), R.layout.collection_widget_layout);

            Intent toFormIntent = new Intent(context, FormActivity.class);
            PendingIntent formPintent = PendingIntent.getActivity(context, 0, toFormIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rvs.setOnClickPendingIntent(R.id.add_button, formPintent);

            Intent in = new Intent(context, CollectionRemoteService.class);
            rvs.setRemoteAdapter(R.id.pInfo_list, in);
            rvs.setEmptyView(R.id.pInfo_list, R.id.empty_text);

            Intent toListIntent = new Intent(context, ViewActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(context, 0, toListIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rvs.setPendingIntentTemplate(R.id.pInfo_list, pIntent);


            appWidgetManager.updateAppWidget(theWidgetId, rvs);


        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);

    }

}
