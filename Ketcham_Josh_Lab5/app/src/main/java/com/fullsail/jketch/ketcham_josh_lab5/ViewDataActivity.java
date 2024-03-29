package com.fullsail.jketch.ketcham_josh_lab5;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class ViewDataActivity extends Activity {

    String titleString;
    String snipetString;
    String pictureString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        Bundle b = getIntent().getExtras();

        titleString = b.getString(StringData.FIELD_ONE);
        snipetString = b.getString(StringData.FIELD_TWO);
        pictureString = b.getString(StringData.IMAGE_DATA);

        TextView title = (TextView) findViewById(R.id.text1);

        title.setText(titleString);

        TextView snippet = (TextView) findViewById(R.id.text2);
        snippet.setText(snipetString);

        ((ImageView) findViewById(R.id.theImage)).setImageBitmap((Bitmap) BitmapFactory.decodeFile(pictureString));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_delete) {

            MapLocationFrag mapFrag = new MapLocationFrag();
            mapFrag.removeMarker();
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
