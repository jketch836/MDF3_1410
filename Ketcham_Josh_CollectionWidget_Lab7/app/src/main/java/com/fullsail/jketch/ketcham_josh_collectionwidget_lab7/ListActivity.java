package com.fullsail.jketch.ketcham_josh_collectionwidget_lab7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.fullsail.jketch.ketcham_josh_collectionwidget_lab7.Fragments.ListViewFragment;
import com.fullsail.jketch.ketcham_josh_collectionwidget_lab7.Fragments.theInterface;


public class ListActivity extends Activity implements theInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getFragmentManager().beginTransaction().replace(R.id.ListFrameLayout, ListViewFragment.newInstance(), ListViewFragment.TAG).commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            Intent toForm = new Intent(this, FormActivity.class);
            startActivity(toForm);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void theInfoMethod() {

    }
}
