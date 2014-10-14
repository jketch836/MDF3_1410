package com.fullsail.jketch.ketcham_josh_la1_imageduplicator;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IntentServiceClass extends IntentService {

    public static final String TAG = "INTENT_SERVICE";

    public IntentServiceClass() {
        super("IntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG, "HELLO FROM SERVICE");

        String theImagePath = intent.getStringExtra(StringsClass.IMAGE_PATH);

        File getImageFile = new File(theImagePath);

        byte[] theBytes = null;

        try {

            FileInputStream fis = new FileInputStream(getImageFile);

            theBytes = IOUtils.toByteArray(fis);

            fis.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        File es = this.getExternalFilesDir(null);

        SimpleDateFormat df = new SimpleDateFormat("MMddyyyy_HHmmss");
        Date thisDay = new Date(System.currentTimeMillis());

        String newImageFormat = df.format(thisDay);

        File fileName = new File(es, newImageFormat + ".dat");

        try {

            FileOutputStream epfos = new FileOutputStream(fileName);

            epfos.write(theBytes);

            epfos.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

            /* EXTERNAL STORAGE END */


        /* NOTIFICATION START */
        NotificationManager nManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);

        BitmapFactory.Options bfo1 = new BitmapFactory.Options();
        bfo1.inSampleSize = 8;

        BitmapFactory.Options bfo2 = new BitmapFactory.Options();
        bfo2.inSampleSize = 6;

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this);
        nBuilder.setSmallIcon(R.drawable.ic_action_picture);
        nBuilder.setLargeIcon(BitmapFactory.decodeFile(fileName.getAbsolutePath(), bfo1));
        nBuilder.setContentTitle("Picture");

        Intent toGallery = new Intent(Intent.ACTION_VIEW);
        toGallery.setDataAndType(Uri.fromFile(fileName), "image/jpeg");
        PendingIntent pBrowserIntent = PendingIntent.getService(this, StringsClass.REQUEST_NOT, toGallery, PendingIntent.FLAG_UPDATE_CURRENT);
        nBuilder.setContentIntent(pBrowserIntent);

        NotificationCompat.BigPictureStyle bigPicNotification = new NotificationCompat.BigPictureStyle();
        nBuilder.setSmallIcon(R.drawable.ic_action_picture);
        bigPicNotification.bigLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_picture));
        bigPicNotification.bigPicture(BitmapFactory.decodeFile(fileName.getAbsolutePath(), bfo2));
        nBuilder.setStyle(bigPicNotification);

        Intent toShare = new Intent(Intent.ACTION_SEND);
        toShare.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(fileName));
        toShare.setType("image/jpeg");
        PendingIntent pShareIntent = PendingIntent.getActivity(this, StringsClass.REQUEST_NOT, toShare, PendingIntent.FLAG_UPDATE_CURRENT);

        nBuilder.addAction(R.drawable.ic_action_share, "Share Picture", pShareIntent);

        Intent toDelete = new Intent(StringsClass.ACTION_DELETE);
        toDelete.putExtra(StringsClass.DELETE_FILE, fileName.getAbsolutePath());
        PendingIntent pDeleteIntent = PendingIntent.getBroadcast(this, StringsClass.REQUEST_NOT, toDelete, PendingIntent.FLAG_UPDATE_CURRENT);

        nBuilder.addAction(R.drawable.ic_action_discard, "Delete Picture", pDeleteIntent);


        nManager.notify(StringsClass.EXPAND_NOT, nBuilder.build());

      /* NOTIFICATION END */

    }

    /* BROADCAST RECEIVER START */
    BroadcastReceiver theReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            GridImageFragment gridFrag = new GridImageFragment();

            if (intent.getAction().equals(StringsClass.IMAGE_PATH)) {

                gridFrag.imageAdapter.notifyDataSetChanged();

            } else if (intent.getAction().equals(StringsClass.ACTION_DELETE)) {

                String filePath = intent.getStringExtra(StringsClass.DELETE_FILE);

                File theFileName = new File(filePath);

                Log.d(TAG, "In delete receiver");

                byte[] updatedBytes = null;

                try {

                    FileInputStream fis = new FileInputStream(filePath);

                    updatedBytes = IOUtils.toByteArray(fis);

                    fis.close();

                } catch (Exception e) {

                    e.printStackTrace();

                }

                theFileName.delete();

                try {

                    FileOutputStream epfos = new FileOutputStream(theFileName);

                    epfos.write(updatedBytes);

                    epfos.close();

                } catch (Exception e) {

                    e.printStackTrace();

                }

                if (!(theFileName.exists())) {

                    Toast.makeText(context, "Image Deleted", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(context, "Error, Please try again", Toast.LENGTH_SHORT).show();

                }

                gridFrag.imageAdapter.notifyDataSetChanged();

            }

        }

    };
    /* BROADCAST RECEIVER END */

}
