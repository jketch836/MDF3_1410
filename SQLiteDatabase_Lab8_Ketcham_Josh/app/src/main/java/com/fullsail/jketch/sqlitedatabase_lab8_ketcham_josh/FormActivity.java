package com.fullsail.jketch.sqlitedatabase_lab8_ketcham_josh;

import android.app.Activity;
import android.app.AlertDialog;
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
import java.text.SimpleDateFormat;
import java.util.Date;


public class FormActivity extends Activity {

    private static final int REQUEST_CAMERA = 0x0202;

    ImageView iv;
    EditText fField;
    EditText lField;
    EditText hDateField;
    EditText hPayField;
    Uri theFileUri;
    File imageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);


        fField = (EditText) findViewById(R.id.firstNameField);
        lField = (EditText) findViewById(R.id.lastNameField);
        hDateField = (EditText) findViewById(R.id.hdateField);
        hPayField = (EditText) findViewById(R.id.hpayField);

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
        if (id == R.id.action_settings) {

            if (fField.getText().length() != 0 && lField.getText().length() != 0 && hDateField.getText().length() != 0
                    && hPayField.getText().length() != 0 && imageData != null) {

                String firstName = fField.getText().toString();
                String lastName = lField.getText().toString();
                String hDateString = hDateField.getText().toString();
                String hPayString = hPayField.getText().toString();
                String imageString = imageData.getAbsolutePath();

                float hPayFloat = Float.parseFloat(hPayString);

                SQLDataBaseHelper db = new SQLDataBaseHelper(this);

                db.addEmployee(firstName, lastName, hDateString, hPayFloat, imageString);

                finish();

            } else {

                new AlertDialog.Builder(FormActivity.this)
                        .setMessage("Please fill in all the Fields")
                        .setPositiveButton("OK",  null)
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

        File appDirectory = new File(imageDirectory, "SQLiteApp");
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
