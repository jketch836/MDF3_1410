package com.fullsail.jketch.ketcham_josh_collectionwidget_lab7;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.fullsail.jketch.ketcham_josh_collectionwidget_lab7.WidgetPackage.CollectionWidgetProvider;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class ViewActivity extends Activity {

    String getFirst;
    String getLast;
    String getAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Bundle getData = getIntent().getExtras();

        if (getData != null) {

            getFirst = getData.getString(StringsClass.FIRST_NAME);
            getLast = getData.getString(StringsClass.LAST_NAME);
            getAge = getData.getString(StringsClass.AGE);

            TextView firstName = (TextView) findViewById(R.id.detailFirstNameField);
            firstName.setText(getFirst);

            TextView lastName = (TextView) findViewById(R.id.detailLastNameField);
            lastName.setText(getLast);

            TextView ageText = (TextView) findViewById(R.id.detailAgeField);
            ageText.setText(getAge);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            ArrayList<PersonInfoClass> formArray = null;

            try {

                FileInputStream fis = this.openFileInput("TheFormData.dat");
                ObjectInputStream ois = new ObjectInputStream(fis);
                formArray = (ArrayList<PersonInfoClass>) ois.readObject();
                ois.close();


            } catch (Exception e) {

                e.printStackTrace();
            }

            if (formArray != null) {

                for (int i = 0; i < formArray.size(); i++) {

                    PersonInfoClass data = formArray.get(i);

                    if (getFirst.equals(data.getfName()) && getLast.equals(data.getlName()) && getAge.equals(data.getAge())) {

                        formArray.remove(i);

                        try {

                            FileOutputStream fos = this.openFileOutput("TheFormData.dat", Context.MODE_PRIVATE);
                            ObjectOutputStream oos = new ObjectOutputStream(fos);
                            oos.writeObject(formArray);

                            AppWidgetManager awm = AppWidgetManager.getInstance(this);

                            int appWidIDs[] = awm.getAppWidgetIds(new ComponentName(this.getPackageName(), CollectionWidgetProvider.class.getName()));

                            awm.notifyAppWidgetViewDataChanged(appWidIDs, R.id.pInfo_list);

                            oos.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                    }

                }
            }

            Intent backToList = new Intent(this, ListActivity.class);
            startActivity(backToList);

        }

        return super.onOptionsItemSelected(item);
    }

}
