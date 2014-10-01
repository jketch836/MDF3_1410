package com.fullsail.jketch.ketcham_josh_lab1_broadcastreceiver_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.fullsail.jketch.ketcham_josh_lab1_broadcastreceiver_project.Fragment.InfoInterface;


public class MainActivity extends Activity implements InfoInterface {

    public static final String TAG = "FORM/MAIN_ACTIVITY";

    public static final String FIRST_NAME = "com.fullsail.android.EXTRA_FIRST_NAME";
    public static final String LAST_NAME = "com.fullsail.android.EXTRA_LAST_NAME";
    public static final String AGE = "com.fullsail.android.EXTRA_AGE";

    public static final String SAVE_DATA = "com.fullsail.android.ACTION_SAVE_DATA";
    public static final String DELETE_DATA = "com.fullsail.android.ACTION_DELETE_DATA";
    public static final String UPDATE_LIST = "com.fullsail.android.ACTION_UPDATE_LIST";
    public static final String VIEW_INFO = "com.fullsail.android.ACTION_VIEW_DATA";

    EditText firstName;
    EditText lastName;
    EditText ageText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = (EditText) findViewById(R.id.firstNameField);
        lastName = (EditText) findViewById(R.id.lastNameField);
        ageText = (EditText) findViewById(R.id.ageField);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_save) {

            if (firstName.getText().length() != 0 && lastName.getText().length() != 0 && ageText.getText().length() != 0) {

                Intent brIntent = new Intent(SAVE_DATA);
                brIntent.putExtra(FIRST_NAME, firstName.getText().toString());
                brIntent.putExtra(LAST_NAME, lastName.getText().toString());
                brIntent.putExtra(AGE, ageText.getText().toString());
                sendBroadcast(brIntent);

            } else {

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setMessage("Please Populate all the Fields Provided");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();

            }


            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void InfoMethod() {

    }
}
