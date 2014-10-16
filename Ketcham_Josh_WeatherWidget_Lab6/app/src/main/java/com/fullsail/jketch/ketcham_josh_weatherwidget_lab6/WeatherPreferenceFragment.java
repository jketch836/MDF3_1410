package com.fullsail.jketch.ketcham_josh_weatherwidget_lab6;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;


public class WeatherPreferenceFragment extends PreferenceFragment {

    public static final String TAG = "PREFERENCE_FRAGMENT";

    int wWidgetId;

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction();
    }

    public WeatherPreferenceFragment() {
        // Required empty public constructor
    }

    public OnFragmentInteractionListener mListener;

    public static WeatherPreferenceFragment newInstance() {
        WeatherPreferenceFragment fragment = new WeatherPreferenceFragment();

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preference_layout);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String selectedTheme = prefs.getString("PREF_THEME", "NOT ASSIGNED");

        AppWidgetManager appWidget = AppWidgetManager.getInstance(getActivity());
        ComponentName componentName = new ComponentName(getActivity(), WeatherAppWidget.class);
        RemoteViews rView = new RemoteViews(getActivity().getPackageName(), R.layout.weather_app_widget);

        if (selectedTheme.equals("0")) {

            rView.setTextColor(wWidgetId, getResources().getColor(R.color.white));

        } else if (selectedTheme.equals("1")) {

            rView.setTextColor(wWidgetId, getResources().getColor(R.color.cyan));

        } else {

            rView.setTextColor(wWidgetId, getResources().getColor(R.color.white));

        }

        appWidget.updateAppWidget(componentName, rView);
    }
}
