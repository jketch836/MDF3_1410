package com.fullsail.jketch.ketcham_josh_lab1_broadcastreceiver_project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class InfoReceiver extends BroadcastReceiver {

    public static final String FIRST_NAME = "com.fullsail.android.EXTRA_FIRST_NAME";
    public static final String LAST_NAME = "com.fullsail.android.EXTRA_LAST_NAME";
    public static final String AGE = "com.fullsail.android.EXTRA_AGE";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(FIRST_NAME)) {



        } else if (intent.getAction().equals(LAST_NAME)) {



        } else if (intent.getAction().equals(AGE)) {



        }
    }

}
