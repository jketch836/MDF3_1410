package com.fullsail.jketch.ketcham_josh_la1_imageduplicator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;


public class ImageBroadcastClass extends BroadcastReceiver {

    public static final String TAG = "BROADCAST_RECEIVER";

    @Override
    public void onReceive(Context context, Intent intent) {

        String imagePathString = getFileUri(context, intent);

        /* TO SERVICE START */
        Intent toService = new Intent(context, IntentServiceClass.class);
        toService.putExtra(StringsClass.IMAGE_PATH, imagePathString);
        context.startService(toService);
        /* TO SERVICE END */

    }

    public String getFileUri(Context context, Intent capturedIntent) {

        Uri selectedImage = capturedIntent.getData();
        String[] pathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = context.getContentResolver().query(selectedImage, pathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(pathColumn[0]);
        String imagePath = cursor.getString(columnIndex);
        cursor.close();

        return imagePath;
    }

}
