package com.fullsail.jketch.sqlitedatabase_lab8_ketcham_josh;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class DetailActivity extends Activity {

    String imageString;
    String lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle b = getIntent().getExtras();

        imageString = b.getString(StringsClass.PROFILE_IMAGE);
        lastName = b.getString(StringsClass.LAST_NAME);

        ImageView iv = (ImageView) findViewById(R.id.profileImage);
        iv.setImageBitmap(BitmapFactory.decodeFile(imageString));

        TextView tv = (TextView) findViewById(R.id.firstNText);
        tv.setText(b.getString(StringsClass.FIRST_NAME));

        tv = (TextView) findViewById(R.id.lastNText);
        tv.setText(b.getString(StringsClass.LAST_NAME));

        tv = (TextView) findViewById(R.id.hireDateText);
        tv.setText(b.getString(StringsClass.HIRE_DATE));

        tv = (TextView) findViewById(R.id.hourlyPayText);
        tv.setText(b.getString(StringsClass.HOUR_PAY));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            SQLDataBaseHelper db = new SQLDataBaseHelper(this);

            db.removeEmployee(imageString, lastName);

            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
