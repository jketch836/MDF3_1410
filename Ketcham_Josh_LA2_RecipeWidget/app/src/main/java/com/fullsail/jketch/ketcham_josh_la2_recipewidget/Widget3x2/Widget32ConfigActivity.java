package com.fullsail.jketch.ketcham_josh_la2_recipewidget.Widget3x2;

import android.app.Activity;
import android.os.Bundle;

import com.fullsail.jketch.ketcham_josh_la2_recipewidget.R;

public class Widget32ConfigActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget32_config);

        getFragmentManager().beginTransaction().replace(R.id.widget_container_two, new PreferenceFragment32(), PreferenceFragment32.TAG).commit();
    }

}
