package com.fullsail.jketch.ketcham_josh_la2_recipewidget.Widget3x1;

import android.app.Activity;
import android.os.Bundle;

import com.fullsail.jketch.ketcham_josh_la2_recipewidget.R;

public class Widget31ConfigActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget31_config);

        getFragmentManager().beginTransaction().replace(R.id.widget_container_one, new PreferenceFragment31(), PreferenceFragment31.TAG).commit();

    }

}
