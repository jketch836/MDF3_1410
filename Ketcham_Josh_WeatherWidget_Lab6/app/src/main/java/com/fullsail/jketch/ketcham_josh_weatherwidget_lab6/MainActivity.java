package com.fullsail.jketch.ketcham_josh_weatherwidget_lab6;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends Activity {

    public static final String TAG = "MAIN_ACTIVITY";

    Boolean first;

    int mWeatherWidgetId;

    ArrayList<WeatherInfo> weatherList = new ArrayList<WeatherInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        ComponentName componentName = new ComponentName(this, WeatherAppWidget.class);

        first = getSharedPreferences("BOOT_NOW", MODE_PRIVATE).getBoolean("firstRun", true);

        if (first) {

            Log.d(TAG, "FIRST_RUN");

            AlarmManager alarmManager = (AlarmManager) this
                    .getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(this, WeatherService.class);
            PendingIntent pIntent = PendingIntent.getService(this, 0, intent,
                    0);

            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + 30000, /* change to 30000 */
                    30000, pIntent);


            finish();


            getSharedPreferences("BOOT_NOW", MODE_PRIVATE).edit().putBoolean("firstRun", false).commit();

        }


        Intent toWeatherDetailIntent = new Intent(this, MainActivity.class);
        toWeatherDetailIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, componentName);
        PendingIntent wDetailpIntent = PendingIntent.getActivity(this, 0, toWeatherDetailIntent, 0);

        Intent toWeatherPreferenceIntent = new Intent(this, WeatherPreferenceActivity.class);
        toWeatherPreferenceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, componentName);
        PendingIntent wPreferncepIntent = PendingIntent.getActivity(this, 0, toWeatherPreferenceIntent, 0);

        SimpleDateFormat sdf = new SimpleDateFormat("h:mm:ss");

        String currentTime = sdf.format(new Date());

        RemoteViews serviceInfoView = new RemoteViews(this.getPackageName(), R.layout.weather_app_widget);
        if (weatherList.size() != 0) {
            serviceInfoView.setTextViewText(R.id.weatherConditionText, weatherList.get(0).getConditions());
            serviceInfoView.setTextViewText(R.id.currentTempText, weatherList.get(0).getHighLow());
            serviceInfoView.setTextViewText(R.id.lastUpdated, currentTime);
        }


        Intent weatherUpdateIntent = new Intent(this, WeatherService.class);
        PendingIntent wUpdatepIntent = PendingIntent.getService(this, 0, weatherUpdateIntent, 0);

        serviceInfoView.setOnClickPendingIntent(R.id.weatherwidget_icon, wDetailpIntent);

        serviceInfoView.setOnClickPendingIntent(R.id.config_button, wPreferncepIntent);

        serviceInfoView.setOnClickPendingIntent(R.id.widgetLayout, wUpdatepIntent);

        appWidgetManager.updateAppWidget(componentName, serviceInfoView);



        Intent intent1 = getIntent();

        if (intent1.hasExtra(AppWidgetManager.EXTRA_APPWIDGET_ID)) {

            mWeatherWidgetId = intent1.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);

        } else {

            mWeatherWidgetId = -1;


        }

        if (mWeatherWidgetId == -1) {

            setResult(RESULT_CANCELED);
            finish();

        }

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mWeatherWidgetId);
        setResult(RESULT_OK, resultValue);

        if (weatherList != null) {

        try {

            FileInputStream fis = this.openFileInput("WeatherData.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            weatherList = (ArrayList<WeatherInfo>) ois.readObject();
            fis.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        if (weatherList == null) {

            weatherList = new ArrayList<WeatherInfo>();

        }

                ((TextView) findViewById(R.id.mWeatherConditions)).setText(weatherList.get(0).getConditions());
                ((TextView) findViewById(R.id.mWeatherTemp)).setText(weatherList.get(0).getTemp());
                ((TextView) findViewById(R.id.mHighLowTemp)).setText(weatherList.get(0).getHighLow());


                ((TextView) findViewById(R.id.mWeatherConditionsD1)).setText(weatherList.get(1).getConditions());
                ((TextView) findViewById(R.id.mWeatherTempD1)).setText(weatherList.get(1).getHighLow());


                ((TextView) findViewById(R.id.mWeatherConditionsD2)).setText(weatherList.get(2).getConditions());
                ((TextView) findViewById(R.id.mWeatherTempD2)).setText(weatherList.get(2).getHighLow());

        }

    }

}
