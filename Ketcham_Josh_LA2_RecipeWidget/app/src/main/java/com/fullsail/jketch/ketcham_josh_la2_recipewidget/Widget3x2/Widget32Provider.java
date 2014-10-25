package com.fullsail.jketch.ketcham_josh_la2_recipewidget.Widget3x2;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.fullsail.jketch.ketcham_josh_la2_recipewidget.R;


public class Widget32Provider extends AppWidgetProvider {

    public static final String ACTION_UPDATE = "com.fullsail.android.collectionwidgetdemo.UPDATE_2";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int i = 0; i < appWidgetIds.length; i++) {

            Intent intent = new Intent(context, Widget32Service.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            Intent forward_intent = new Intent(Widget32Factory.FORWARD_ACTION_2);
            PendingIntent forward_pIntent = PendingIntent.getBroadcast(context, 0, forward_intent, 0);

            Intent previous_intent = new Intent(Widget32Factory.PREVIOUS_ACTION_2);
            PendingIntent previous_pIntent = PendingIntent.getBroadcast(context, 0, previous_intent, 0);

            RemoteViews rvs = new RemoteViews(context.getPackageName(), R.layout.widget32_layout);
            rvs.setRemoteAdapter(R.id.avfWidget32, intent);
            rvs.setEmptyView(R.id.avfWidget32, R.id.empty_textView_32);

            rvs.setOnClickPendingIntent(R.id.nextBTN32, forward_pIntent);
            rvs.setOnClickPendingIntent(R.id.previousBTN32, previous_pIntent);

            appWidgetManager.updateAppWidget(appWidgetIds[i], rvs);

        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (intent.getAction().equals(ACTION_UPDATE)) {

            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context.getPackageName(), Widget32Provider.class.getName());
            int[] ids = manager.getAppWidgetIds(componentName);

            manager.notifyAppWidgetViewDataChanged(ids, R.id.avfWidget32);


        } else if (intent.getAction().equals(Widget32Factory.FORWARD_ACTION_2)) {

            AppWidgetManager manager = AppWidgetManager.getInstance(context);

            ComponentName componentName = new ComponentName(context.getPackageName(), Widget32Provider.class.getName());
            int[] ids = manager.getAppWidgetIds(componentName);

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget32_layout);
            rv.setRemoteAdapter(R.id.avfWidget32, intent);
            rv.setEmptyView(R.id.avfWidget32, R.id.empty_textView_32);
            rv.showNext(R.id.avfWidget32);

            manager.updateAppWidget(ids, rv);


        } else if (intent.getAction().equals(Widget32Factory.PREVIOUS_ACTION_2)) {

            AppWidgetManager manager = AppWidgetManager.getInstance(context);

            ComponentName componentName = new ComponentName(context.getPackageName(), Widget32Provider.class.getName());
            int[] ids = manager.getAppWidgetIds(componentName);

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget32_layout);
            rv.setRemoteAdapter(R.id.avfWidget32, intent);
            rv.setEmptyView(R.id.avfWidget32, R.id.empty_textView_32);
            rv.showPrevious(R.id.avfWidget32);

            manager.updateAppWidget(ids, rv);


        }

    }

}


