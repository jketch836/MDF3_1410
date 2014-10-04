package com.fullsail.jketch.ketcham_josh_lab2_intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class IntentServiceClass extends IntentService {

    public static final String TAG = "INTENT_SERVICE_CLASS";

    public IntentServiceClass() {

        super("IntentServiceClass");

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        ArrayList<String> urlList = new ArrayList<String>();

        urlList.add("http://i.imgur.com/MgmzpOJ.jpg");
        urlList.add("http://i.imgur.com/K6b7OTK.jpg");
        urlList.add("http://i.imgur.com/VZmFngH.jpg");
        urlList.add("http://i.imgur.com/ptE5z9u.jpg");
        urlList.add("http://i.imgur.com/4QKO8Up.jpg");
        urlList.add("http://i.imgur.com/Vm2UdDH.jpg");
        urlList.add("http://i.imgur.com/C040ctB.jpg");
        urlList.add("http://i.imgur.com/tM1bsAH.jpg");
        urlList.add("http://i.imgur.com/fS1lKZx.jpg");
        urlList.add("http://i.imgur.com/tM1bsAH.jpg");
        urlList.add("http://i.imgur.com/7ZSQfIu.jpg");
        urlList.add("http://i.imgur.com/XLJiKqp.jpg");
        urlList.add("http://i.imgur.com/nXVLE9W.jpg");
        urlList.add("http://i.imgur.com/HYQuj4b.jpg");
        urlList.add("http://i.imgur.com/wYXWJZz.jpg");
        urlList.add("http://i.imgur.com/En3J4ZF.jpg");
        urlList.add("http://i.imgur.com/R8YIb8d.jpg");
        urlList.add("http://i.imgur.com/cLv3TVc.jpg");
        urlList.add("http://i.imgur.com/f7pMMdA.jpg");
        urlList.add("http://i.imgur.com/o7iUQrS.jpg");
        urlList.add("http://i.imgur.com/Z2WQBPI.jpg");
        urlList.add("http://i.imgur.com/Dl1aIHV.jpg");
        urlList.add("http://i.imgur.com/1oyYfr0.jpg");
        urlList.add("http://i.imgur.com/YSJ28fr.jpg");
        urlList.add("http://i.imgur.com/Ey39hl5.jpg");
        urlList.add("http://i.imgur.com/HAnhjCI.jpg");
        urlList.add("http://i.imgur.com/wr65Geg.jpg");
        urlList.add("http://i.imgur.com/o7iUQrS.jpg");
        urlList.add("http://i.imgur.com/yZg8FKn.jpg");
        urlList.add("http://i.imgur.com/mxfqLzO.jpg");
        urlList.add("http://i.imgur.com/z09HynQ.jpg");
        urlList.add("http://i.imgur.com/8gSbVQs.jpg");
        urlList.add("http://i.imgur.com/Mrmp9IH.jpg");

        File es = this.getExternalFilesDir(null);

        try {

            for (int i = 0; i < urlList.size(); i++) {

                String aURL = urlList.get(i);

                URL theURLs = new URL(aURL);

                HttpURLConnection connection = (HttpURLConnection) theURLs.openConnection();

                connection.connect();

                InputStream input = connection.getInputStream();

                byte[] thebytearray = IOUtils.toByteArray(input);

                File aFile = new File(es, "Console_Image_" + i + ".dat");

                Intent toShowPicture = new Intent(GridFragment.UPDATE_IMAGES);
                sendBroadcast(toShowPicture);

            /* If check to make sure images were not downloaded a second time */
                if (es != null) {

                    if (!(aFile.exists())) {

                        FileOutputStream fos = new FileOutputStream(aFile, false);

                        Log.d(TAG, "" + aFile);

                        fos.write(thebytearray);

                        fos.close();

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
