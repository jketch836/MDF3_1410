package com.fullsail.jketch.ketchamjoshlab4mediaplayer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity implements MediaListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View mediaView = findViewById(R.id.mediaFrame);

        if (mediaView.getTag().equals("portrait")) {

            getFragmentManager().beginTransaction()
                    .replace(R.id.mediaFrame, MediaPortraitFragment.newInstance(), MediaPortraitFragment.TAG)
                    .commit();

        } else {

            getFragmentManager().beginTransaction()
                    .replace(R.id.mediaFrame, MediaLandscapeFragment.newInstance(), MediaLandscapeFragment.TAG)
                    .commit();

        }
    }

    @Override
    public void MediaListenerFunc() {

    }
}
