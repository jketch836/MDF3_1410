package com.fullsail.jketch.sqlitedatabase_lab8_ketcham_josh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.fullsail.jketch.sqlitedatabase_lab8_ketcham_josh.Fragments.ListViewFragment;
import com.fullsail.jketch.sqlitedatabase_lab8_ketcham_josh.Fragments.TheInterface;


public class MainActivity extends Activity implements TheInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().replace(R.id.toListFrag, ListViewFragment.newInstance(), ListViewFragment.TAG).commit();

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

            Intent toForm = new Intent(this, FormActivity.class);
            startActivityForResult(toForm, 0);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       ListViewFragment listFrag = (ListViewFragment) getFragmentManager().findFragmentByTag(ListViewFragment.TAG);

       listFrag.refreshList();

    }

    @Override
    public void infoMethod() {

    }
}
