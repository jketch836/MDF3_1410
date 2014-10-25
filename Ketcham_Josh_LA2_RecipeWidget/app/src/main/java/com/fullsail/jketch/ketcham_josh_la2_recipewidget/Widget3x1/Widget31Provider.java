package com.fullsail.jketch.ketcham_josh_la2_recipewidget.Widget3x1;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.fullsail.jketch.ketcham_josh_la2_recipewidget.R;

/**
 * Implementation of App Widget functionality.
 */
public class Widget31Provider extends AppWidgetProvider {

    public static final String ACTION_UPDATE = "com.fullsail.android.collectionwidgetdemo.UPDATE_1";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {

            int theWidgetId = appWidgetIds[i];

            Intent intent = new Intent(context, Widget31Service.class);

            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews rvs = new RemoteViews(context.getPackageName(), R.layout.widget31_layout);
            rvs.setRemoteAdapter(R.id.avfWidget31, intent);
            rvs.setEmptyView(R.id.avfWidget31, R.id.empty_textView_31);

            appWidgetManager.updateAppWidget(theWidgetId, rvs);

        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (intent.getAction().equals(ACTION_UPDATE)) {

            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context.getPackageName(), Widget31Provider.class.getName());
            int[] ids = manager.getAppWidgetIds(componentName);

            manager.notifyAppWidgetViewDataChanged(ids, R.id.avfWidget31);

        }

    }

}


