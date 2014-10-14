package com.fullsail.jketch.ketcham_josh_la1_imageduplicator;

import android.app.Activity;
import android.os.Bundle;


public class MainActivity extends Activity implements GridImageFragment.GridFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().replace(R.id.toGridLayout, GridImageFragment.newInstance(), GridImageFragment.TAG).commit();

    }

    @Override
    public void onFragmentInteraction() {

    }
}
