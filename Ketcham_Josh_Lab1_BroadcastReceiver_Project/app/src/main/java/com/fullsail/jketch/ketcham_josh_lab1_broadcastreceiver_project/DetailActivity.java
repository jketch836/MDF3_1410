package com.fullsail.jketch.ketcham_josh_lab1_broadcastreceiver_project;

import android.app.Activity;
import android.os.Bundle;

import com.fullsail.jketch.ketcham_josh_lab1_broadcastreceiver_project.Fragment.DetailsFragment;
import com.fullsail.jketch.ketcham_josh_lab1_broadcastreceiver_project.Fragment.InfoInterface;


public class DetailActivity extends Activity implements InfoInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getFragmentManager().beginTransaction()
                .replace(R.id.detailContainer, DetailsFragment.newInstance(), DetailsFragment.TAG)
                .commit();

    }

    @Override
    public void InfoMethod() {

    }
}
