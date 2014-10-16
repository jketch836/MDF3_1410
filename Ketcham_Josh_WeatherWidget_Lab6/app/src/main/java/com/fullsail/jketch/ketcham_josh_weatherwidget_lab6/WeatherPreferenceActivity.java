package com.fullsail.jketch.ketcham_josh_weatherwidget_lab6;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class WeatherPreferenceActivity extends Activity implements WeatherPreferenceFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_preference);

        getFragmentManager().beginTransaction()
                .replace(R.id.PreferenceLayout, WeatherPreferenceFragment.newInstance(), WeatherPreferenceFragment.TAG)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather_preference, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            saveWeatherInfo();
            
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction() {

    }

    public void saveWeatherInfo() {

       Bundle b = getIntent().getExtras();

        String wConditionString = b.getString(StringClass.WEATHER_CONDITIONS);
        String wTempHighLowString = b.getString(StringClass.TEMP_HIGH_LOW);
        String wTemp = b.getString(StringClass.TEMP);

        ArrayList<WeatherInfo> theWeatherInfo;
        
        theWeatherInfo = null;

        try {

            FileInputStream fis = this.openFileInput("WeatherData.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            theWeatherInfo = (ArrayList<WeatherInfo>) ois.readObject();
            fis.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        if (theWeatherInfo == null) {

            theWeatherInfo = new ArrayList<WeatherInfo>();

        }

        theWeatherInfo.add(new WeatherInfo(wConditionString, wTempHighLowString, wTemp, null));

        try {

            FileOutputStream fos = this.openFileOutput("WeatherData.dat", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(theWeatherInfo);
            oos.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
