package com.fullsail.jketch.ketcham_josh_la2_recipewidget.Widget3x1;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RemoteViews;

import com.fullsail.jketch.ketcham_josh_la2_recipewidget.DetailActivity;
import com.fullsail.jketch.ketcham_josh_la2_recipewidget.R;
import com.fullsail.jketch.ketcham_josh_la2_recipewidget.Widget3x2.Widget32Service;

/**
 * Created by jketch on 10/23/14.
 */
public class PreferenceFragment31 extends PreferenceFragment{

    public static final String TAG = "PREFERENCE_TAG";

    int widgetIDInt = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addPreferencesFromResource(R.xml.preference_settings);

        widgetIDInt = getActivity().getIntent().getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.detail, menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.action_settings:

                Intent intent = new Intent(getActivity(), Widget32Service.class);
                RemoteViews widgetViews = new RemoteViews(getActivity().getPackageName(), R.layout.widget31_layout);
                widgetViews.setRemoteAdapter(R.id.avfWidget31, intent);
                widgetViews.setEmptyView(R.id.avfWidget31, R.id.empty_textView_31);

                Intent toDetails = new Intent(getActivity(), DetailActivity.class);
                PendingIntent pi = PendingIntent.getActivity(getActivity(), 0, toDetails, PendingIntent.FLAG_UPDATE_CURRENT);
                widgetViews.setPendingIntentTemplate(R.id.avfWidget31, pi);


                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getActivity().getApplicationContext());

                appWidgetManager.updateAppWidget(widgetIDInt, widgetViews);

                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetIDInt);
                getActivity().setResult(getActivity().RESULT_OK, resultValue);
                getActivity().finish();

                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
