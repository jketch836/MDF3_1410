package com.fullsail.jketch.ketcham_josh_lab1_broadcastreceiver_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.fullsail.jketch.ketcham_josh_lab1_broadcastreceiver_project.Fragment.InfoInterface;
import com.fullsail.jketch.ketcham_josh_lab1_broadcastreceiver_project.Fragment.ListViewFragment;


public class ListActivity extends Activity implements InfoInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getFragmentManager().beginTransaction()
                .replace(R.id.infoContainer, ListViewFragment.newInstance(), ListViewFragment.TAG)
                .commit();
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
            return true;
        }
        if (id == R.id.add_item) {

            Intent toFormIntent = new Intent(ListActivity.this, MainActivity.class);
            startActivity(toFormIntent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void InfoMethod() {

    }
}
