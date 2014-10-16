package com.fullsail.jketch.ketcham_josh_weatherwidget_lab6;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class WeatherService extends IntentService {

    ArrayList<WeatherInfo> weatherList = new ArrayList<WeatherInfo>();

    String temp;
    String tempHighLow;
    String weatherCondition;
    String wIcon;

    public WeatherService() {
        super("Weather Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {


        String weatherJsonString = "http://api.wunderground.com/api/4e7dc583deb76743/forecast/conditions/q/" + PrefLocationMethod() +".json";

        AppWidgetManager appWidget = AppWidgetManager.getInstance(this);
        ComponentName componentName = new ComponentName(this, WeatherAppWidget.class);

        try {

            URL wUrl = new URL(weatherJsonString);

            HttpURLConnection connection = (HttpURLConnection) wUrl.openConnection();

            connection.connect();

            InputStream is = connection.getInputStream();

            String wJsonString = IOUtils.toString(is);

            is.close();

            connection.disconnect();

            JSONObject weatherObject = new JSONObject(wJsonString);

            JSONObject theConditions = weatherObject.getJSONObject("current_observation");

            if (theConditions.has("temperature_string")) {

                temp = theConditions.getString("temperature_string");

            }

            JSONObject theForecast = weatherObject.getJSONObject("forecast");

            JSONObject simpleForecast = theForecast.getJSONObject("simpleforecast");

            JSONArray threeDayObservation = simpleForecast.getJSONArray("forecastday");

            for (int i = 0; i < threeDayObservation.length() - 1; i++) {

                JSONObject threeDay = threeDayObservation.getJSONObject(i);

                JSONObject high = threeDay.getJSONObject("high");
                JSONObject low = threeDay.getJSONObject("low");

                if (high.has("fahrenheit") && low.has("fahrenheit")) {
                    tempHighLow = high.getString("fahrenheit") + " / " + low.getString("fahrenheit");
                } else {
                    tempHighLow = "N/A";
                }

                Log.d("HIGH/LOW", tempHighLow);


                if (threeDay.has("conditions")) {
                    weatherCondition = threeDay.getString("conditions");
                } else {
                    weatherCondition = "N/A";
                }
                Log.d("CONDITION", weatherCondition);


                if (threeDay.has("icon_url")) {

                    wIcon = threeDay.getString("icon_url");

                }

                weatherList.add(new WeatherInfo(weatherCondition, tempHighLow, temp, wIcon));

            }

            SimpleDateFormat sdf = new SimpleDateFormat("h:mm:ss aa");

            String currentTime = sdf.format(new Date());

            Intent toWeatherDetailIntent = new Intent(this, MainActivity.class);
            toWeatherDetailIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, componentName);
            PendingIntent wDetailpIntent = PendingIntent.getActivity(this, 0, toWeatherDetailIntent, 0);

            Intent toWeatherPreferenceIntent = new Intent(this, WeatherPreferenceActivity.class);
            toWeatherPreferenceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, componentName);
            PendingIntent wPreferncepIntent = PendingIntent.getActivity(this, 0, toWeatherPreferenceIntent, 0);

            RemoteViews serviceInfoView = new RemoteViews(this.getPackageName(), R.layout.weather_app_widget);

            serviceInfoView.setImageViewBitmap(R.id.weatherwidget_icon, getBitmap(weatherList.get(0).getBitmap()));
            serviceInfoView.setTextViewText(R.id.weatherConditionText, weatherList.get(0).getConditions());
            serviceInfoView.setTextViewText(R.id.currentTempText, weatherList.get(0).getHighLow());
            serviceInfoView.setTextViewText(R.id.lastUpdated, currentTime);


            Intent weatherUpdateIntent = new Intent(this, WeatherService.class);
            PendingIntent wUpdatepIntent = PendingIntent.getService(this, 0, weatherUpdateIntent, 0);


            serviceInfoView.setOnClickPendingIntent(R.id.weatherwidget_icon, wDetailpIntent);

            serviceInfoView.setOnClickPendingIntent(R.id.config_button, wPreferncepIntent);

            serviceInfoView.setOnClickPendingIntent(R.id.widgetLayout, wUpdatepIntent);

            appWidget.updateAppWidget(componentName, serviceInfoView);

            saveInfo();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    protected String PrefLocationMethod() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String selectedLocation = prefs.getString("PREF_LOCATION", "NOT ASSIGNED");

        String string;

        if (selectedLocation.equals("0")) {

            string = "FL/Winter_Park";

        } else if (selectedLocation.equals("1")) {

            string = "MI/Lansing";

        } else if (selectedLocation.equals("2")) {

            string = "VT/Moretown";

        } else {

            string = "FL/Winter_Park";

        }

        return string;

    }

    public Bitmap getBitmap(String stringURL) {

        Bitmap bitmap = null;

        try {

            URL url = new URL(stringURL);

            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        } catch (Exception e) {

            e.printStackTrace();

        }

        return bitmap;

    }


    protected void saveInfo () {

        weatherList = null;

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

        weatherList.add(new WeatherInfo(weatherCondition, tempHighLow, temp, wIcon));

        try {

            FileOutputStream fos = this.openFileOutput("WeatherData.dat", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(weatherList);
            oos.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

}

