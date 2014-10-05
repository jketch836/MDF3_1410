package com.fullsail.jketch.ketcham_josh_lab3_notification;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.fullsail.jketch.ketcham_josh_lab3_notification.Fragments.ReadingListFragment;
import com.fullsail.jketch.ketcham_josh_lab3_notification.Fragments.aInterface;


public class MainActivity extends Activity implements aInterface {

    Boolean firstRun;

    public static final String TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstRun = getSharedPreferences("BOOT_NOW", MODE_PRIVATE).getBoolean("firstRun", true);

        if (firstRun) {

            Log.d(TAG, "FIRST_RUN");

            AlarmManager alarmManager = (AlarmManager) this
                    .getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(this, theIntentService.class);
            PendingIntent pIntent = PendingIntent.getService(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

//TODO
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + 30000, /* change to 30000 */
                    30000, pIntent);

            getSharedPreferences("BOOT_NOW", MODE_PRIVATE).edit().putBoolean("firstRun", false).commit();

        }

        Log.d(TAG, "ON_CREATE");

        getFragmentManager().beginTransaction()
                .replace(R.id.AlarmContainer, ReadingListFragment.newInstance(), ReadingListFragment.TAG)
                .commit();
    }

    @Override
    public void theInterface() {

    }
}
