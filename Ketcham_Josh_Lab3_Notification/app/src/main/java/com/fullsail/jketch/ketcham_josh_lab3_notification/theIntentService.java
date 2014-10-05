package com.fullsail.jketch.ketcham_josh_lab3_notification;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;


public class theIntentService extends IntentService {

    public static final String TAG = "INTENT_SERVICE";

    public theIntentService() {
        super("theIntentService");
    }

    String newsSummary;
    String newsTitle;
    String newsURL;
    NotificationManager mManager;

    ArrayList<NewsInfo> newsList = new ArrayList<NewsInfo>();

    Random randomInt = new Random();

    @Override
    protected void onHandleIntent(Intent intent) {

        mManager = (NotificationManager)this.getSystemService(NOTIFICATION_SERVICE);

        String aURL = "http://api.feedzilla.com/v1/categories/26/articles.json";

        try {

            URL theURLs = new URL(aURL);

            HttpURLConnection connection = (HttpURLConnection) theURLs.openConnection();

            connection.connect();

            InputStream inputStream = connection.getInputStream();

            String jsonString = IOUtils.toString(inputStream);

            inputStream.close();

            connection.disconnect();

            JSONObject newsObject = new JSONObject(jsonString);

            JSONArray articleArray = newsObject.getJSONArray("articles");

            for (int i = 0; i < articleArray.length(); i++) {

                JSONObject info = articleArray.getJSONObject(i);

                if (info.has("summary")) {

                    newsSummary = info.getString("summary");

                } else {

                    newsSummary = "Summary N/A";

                }
                Log.d(TAG, newsSummary);


                if (info.has("title")) {

                    newsTitle = info.getString("title");

                } else {

                    newsTitle = "Title N/A";

                }
                Log.d(TAG, newsTitle);


                if (info.has("url")) {

                    newsURL = info.getString("url");

                }
                Log.d(TAG, newsURL);


                newsList.add(new NewsInfo(newsSummary, newsTitle, newsURL));
            }

            int randomNews = randomInt.nextInt(10);

            NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this);
            nBuilder.setSmallIcon(R.drawable.ic_launcher);
            nBuilder.setContentTitle(newsList.get(randomNews).getTitle());
            nBuilder.setAutoCancel(true);

            Intent toBrowser = new Intent(Intent.ACTION_VIEW);
            toBrowser.setData(Uri.parse(newsList.get(randomNews).getURL()));
            PendingIntent pBrowserIntent = PendingIntent.getActivity(this, TheStrings.REQUEST_NOT, toBrowser, PendingIntent.FLAG_UPDATE_CURRENT);
            nBuilder.setContentIntent(pBrowserIntent);

            NotificationCompat.BigTextStyle longNotification = new NotificationCompat.BigTextStyle();
            longNotification.setBigContentTitle(newsList.get(randomNews).getTitle());
            longNotification.bigText(newsList.get(randomNews).getSummary());
            nBuilder.setStyle(longNotification);
            nBuilder.setGroupSummary(true);

            Intent toSave = new Intent(TheStrings.ACTION_SAVE);
            toSave.putExtra(TheStrings.TITLE, newsList.get(randomNews).getTitle());
            toSave.putExtra(TheStrings.URL, newsList.get(randomNews).getURL());
            PendingIntent pSaveIntent = PendingIntent.getBroadcast(this, TheStrings.REQUEST_NOT, toSave, PendingIntent.FLAG_UPDATE_CURRENT);

//            Intent refresh = new Intent(this, MainActivity.class);
//            PendingIntent prefreashIntent = PendingIntent.getBroadcast(this, TheStrings.REQUEST_NOT, refresh, PendingIntent.FLAG_UPDATE_CURRENT);


            nBuilder.addAction(R.drawable.ic_launcher, "Save Article", pSaveIntent);

            mManager.notify(TheStrings.EXPAND_NOT, nBuilder.build());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
