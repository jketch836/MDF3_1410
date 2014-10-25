package com.fullsail.jketch.ketcham_josh_la2_recipewidget.Widget3x2;

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

/**
 * Created by jketch on 10/23/14.
 */
public class PreferenceFragment32 extends PreferenceFragment{

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

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getActivity().getApplicationContext());

                Intent forward_intent = new Intent(getActivity(), Widget32Provider.class);
                forward_intent.setAction(Widget32Factory.FORWARD_ACTION_2);
                PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), 0, forward_intent, 0);

                Intent back_intent = new Intent(getActivity(), Widget32Provider.class);
                back_intent.setAction(Widget32Factory.PREVIOUS_ACTION_2);
                PendingIntent back_pIntent = PendingIntent.getBroadcast(getActivity(), 0, back_intent, 0);


                Intent intent = new Intent(getActivity(), Widget32Service.class);

                Intent toDetails = new Intent(getActivity(), DetailActivity.class);

                PendingIntent pi = PendingIntent.getActivity(getActivity(), 0, toDetails, PendingIntent.FLAG_UPDATE_CURRENT);

                RemoteViews rvs = new RemoteViews(getActivity().getPackageName(), R.layout.widget32_layout);
                rvs.setRemoteAdapter(R.id.avfWidget32, intent);
                rvs.setEmptyView(R.id.avfWidget32, R.id.empty_textView_32);
                rvs.setPendingIntentTemplate(R.id.avfWidget32, pi);
                rvs.setOnClickPendingIntent(R.id.nextBTN32, pIntent);
                rvs.setOnClickPendingIntent(R.id.previousBTN32, back_pIntent);
                rvs.setPendingIntentTemplate(R.id.avfWidget32, pi);
                rvs.setPendingIntentTemplate(R.id.recipeImageWidget32, pi);

                appWidgetManager.updateAppWidget(widgetIDInt, rvs);

                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetIDInt);
                getActivity().setResult(getActivity().RESULT_OK, resultValue);
                getActivity().finish();

        }

        return super.onOptionsItemSelected(item);
    }
}
