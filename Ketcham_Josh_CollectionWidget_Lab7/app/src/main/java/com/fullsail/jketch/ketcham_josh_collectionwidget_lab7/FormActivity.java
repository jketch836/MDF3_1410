package com.fullsail.jketch.ketcham_josh_collectionwidget_lab7;

import android.app.Activity;
import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.fullsail.jketch.ketcham_josh_collectionwidget_lab7.WidgetPackage.CollectionWidgetProvider;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class FormActivity extends Activity {

    EditText firstName;
    EditText lastName;
    EditText ageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        firstName = (EditText) findViewById(R.id.firstNameField);
        lastName = (EditText) findViewById(R.id.lastNameField);
        ageText = (EditText) findViewById(R.id.ageField);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            if (firstName.getText().length() != 0 && lastName.getText().length() != 0 && ageText.getText().length() != 0) {

                String fNameString = firstName.getText().toString();
                String lNameString = lastName.getText().toString();
                String ageString = ageText.getText().toString();

                PersonInfoClass formInfo = new PersonInfoClass(fNameString, lNameString, ageString);

                ArrayList<PersonInfoClass> formInfoArray = null;

                try {

                    FileInputStream fis = this.openFileInput("TheFormData.dat");
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    formInfoArray = (ArrayList<PersonInfoClass>) ois.readObject();

                    AppWidgetManager awm = AppWidgetManager.getInstance(this);

                    int appWidIDs[] = awm.getAppWidgetIds(new ComponentName(this.getPackageName(), CollectionWidgetProvider.class.getName()));

                    awm.notifyAppWidgetViewDataChanged(appWidIDs, R.id.pInfo_list);

                    ois.close();

                } catch (Exception e) {

                    e.printStackTrace();
                }

                if (formInfoArray == null) {

                    formInfoArray = new ArrayList<PersonInfoClass>();

                }

                formInfoArray.add(formInfo);

                try {

                    FileOutputStream fos = this.openFileOutput("TheFormData.dat", Context.MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(formInfoArray);

                    oos.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                finish();

            } else {

                AlertDialog.Builder alert = new AlertDialog.Builder(FormActivity.this);
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
}
