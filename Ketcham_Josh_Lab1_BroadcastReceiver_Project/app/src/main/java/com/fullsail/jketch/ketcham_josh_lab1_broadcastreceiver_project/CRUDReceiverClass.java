package com.fullsail.jketch.ketcham_josh_lab1_broadcastreceiver_project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
* Created by jketch on 9/30/14.
*/
public class CRUDReceiverClass extends BroadcastReceiver {

    public static final String SAVE_DATA = "com.fullsail.android.ACTION_SAVE_DATA";
    public static final String DELETE_DATA = "com.fullsail.android.ACTION_DELETE_DATA";
    public static final String UPDATE_LIST = "com.fullsail.android.ACTION_UPDATE_LIST";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(SAVE_DATA)) {

            Bundle getData = intent.getExtras();

            String getFirst = getData.getString(MainActivity.FIRST_NAME);
            String getLast = getData.getString(MainActivity.LAST_NAME);
            String getAge = getData.getString(MainActivity.AGE);

            FormData formInfo = new FormData(getFirst, getLast, getAge);

            try {
                File external = context.getExternalFilesDir(null);
                FileOutputStream fos = context.openFileOutput(formInfo.getfName() + ".dat", Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(formInfo);
                oos.close();

                Log.d("THE_FILE", external.getAbsolutePath());

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (intent.getAction().equals(DELETE_DATA)) {



        }
    }


}
