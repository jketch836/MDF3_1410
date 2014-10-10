package com.fullsail.jketch.ketcham_josh_lab5;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FormActivity extends Activity {

    private static final int REQUEST_CAMERA = 0x0202;

    ImageView iv;
    EditText fieldOne;
    EditText fieldTwo;
    Uri theFileUri;
    File imageData;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        b = getIntent().getExtras();

        fieldOne = (EditText) findViewById(R.id.field1);
        fieldTwo = (EditText) findViewById(R.id.field2);

        iv = (ImageView) findViewById(R.id.image);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                theFileUri = getUriOutput();

                if (theFileUri != null) {
                    i.putExtra(MediaStore.EXTRA_OUTPUT, theFileUri);

                }
                startActivityForResult(i, REQUEST_CAMERA);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA && requestCode != RESULT_CANCELED) {

            if (theFileUri == null) {

                iv.setImageBitmap((Bitmap) data.getParcelableExtra("data"));

            } else {

                Bitmap map = BitmapFactory.decodeFile(theFileUri.getPath());
                iv.setImageBitmap(map);
                fileScan(theFileUri);

            }

        }

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
        if (id == R.id.action_save) {

            double geoLat = (double) b.getLong(StringData.GEO_LOC_LAT);
            double geoLng = (double) b.getLong(StringData.GEO_LOC_LNG);

            if (fieldOne.getText().length() != 0 && fieldTwo.getText().length() != 0 && imageData != null) {

                ArrayList<MapInfo> mapArray = null;

                try {

                    FileInputStream fis = this.openFileInput("TheGeoData.dat");
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    mapArray = (ArrayList<MapInfo>) ois.readObject();

                } catch (Exception e) {

                    e.printStackTrace();
                }

                if (mapArray == null) {

                    mapArray = new ArrayList<MapInfo>();

                }

                mapArray.add(new MapInfo(fieldOne.getText().toString(), fieldTwo.getText().toString(), imageData, geoLat, geoLng));

                try {

                    FileOutputStream fos = this.openFileOutput("TheGeoData.dat", Context.MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(mapArray);
                    oos.close();

                } catch (Exception e) {

                    e.printStackTrace();
                }

                Intent toList = new Intent(FormActivity.this, MainActivity.class);
                startActivity(toList);

            } else {

                new AlertDialog.Builder(FormActivity.this)
                        .setMessage("Please Populate all the Fields.")
                        .setPositiveButton("OK", null)
                        .show();

            }

            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public Uri getUriOutput() {
        String theImageName = new SimpleDateFormat("MMddyyyy_HHmmss")
                .format(new Date(System.currentTimeMillis()));

        File imageDirectory = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);

        File appDirectory = new File(imageDirectory, "Lab5App");
        appDirectory.mkdirs();

        imageData = new File(appDirectory, theImageName + ".jpg");

        try {

            imageData.createNewFile();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return Uri.fromFile(imageData);

    }

    private void fileScan(Uri imageUri) {

        Intent scanningIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanningIntent.setData(imageUri);
        sendBroadcast(scanningIntent);

    }

}
