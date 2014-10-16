package com.fullsail.jketch.ketcham_josh_weatherwidget_lab6;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.ArrayList;


public class WeatherAppWidget extends AppWidgetProvider {
    
    ArrayList<WeatherInfo> arrayList = new ArrayList<WeatherInfo>();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for (int i=0; i<appWidgetIds.length; i++) {

            int theWidgetId = appWidgetIds[i];

            Intent toWeatherDetailIntent = new Intent(context, MainActivity.class);
            toWeatherDetailIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, theWidgetId);
            PendingIntent wDetailpIntent = PendingIntent.getActivity(context, 0, toWeatherDetailIntent, 0);

            Intent toWeatherPreferenceIntent = new Intent(context, WeatherPreferenceActivity.class);
            toWeatherPreferenceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, theWidgetId);
            if (arrayList.size() != 0) {
                toWeatherDetailIntent.putExtra(StringClass.WEATHER_CONDITIONS, arrayList.get(0).getConditions());
                toWeatherDetailIntent.putExtra(StringClass.WEATHER_CONDITIONS1, arrayList.get(1).getConditions());
                toWeatherDetailIntent.putExtra(StringClass.WEATHER_CONDITIONS2, arrayList.get(2).getConditions());
                toWeatherDetailIntent.putExtra(StringClass.TEMP_HIGH_LOW, arrayList.get(0).getHighLow());
                toWeatherDetailIntent.putExtra(StringClass.TEMP_HIGH_LOW1, arrayList.get(1).getHighLow());
                toWeatherDetailIntent.putExtra(StringClass.TEMP_HIGH_LOW2, arrayList.get(2).getHighLow());
                toWeatherDetailIntent.putExtra(StringClass.TEMP, arrayList.get(0).getTemp());
            }
            PendingIntent wPreferncepIntent = PendingIntent.getActivity(context, 0, toWeatherPreferenceIntent, 0);

            RemoteViews wInfoWidgetView = new RemoteViews(context.getPackageName(), R.layout.weather_app_widget);

            Intent weatherUpdateIntent = new Intent(context, WeatherService.class);
            PendingIntent wUpdatepIntent = PendingIntent.getService(context, 0, weatherUpdateIntent, 0);

            wInfoWidgetView.setOnClickPendingIntent(R.id.weatherwidget_icon, wDetailpIntent);

            wInfoWidgetView.setOnClickPendingIntent(R.id.config_button, wPreferncepIntent);

            wInfoWidgetView.setOnClickPendingIntent(R.id.widgetLayout, wUpdatepIntent);

            appWidgetManager.updateAppWidget(theWidgetId, wInfoWidgetView);

        }

    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);


    }

}


