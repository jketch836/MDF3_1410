package com.fullsail.jketch.ketchamjoshlab4mediaplayer;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;


public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {


    public static final String TAG = "MEDIA_SERVICE";
    private static final String ACTION_PLAY = "com.fullsail.myAction.PLAY";


    MediaPlayer player;

    boolean musicResumed;

    boolean musicPrep;

    boolean playWhenDone = false;

    int musicPosition = 0;

    int duration;

    public String totalDuration;


    ArrayList<SongClass> songList = new ArrayList<SongClass>();

    public class aBinder extends Binder {

        public MusicService getService() {
            return MusicService.this;
        }

    }


    @Override
    public void onCreate() {
        super.onCreate();

        musicPrep = musicResumed = false;

        String batman = "android.resource://" + getPackageName() + "/raw/batman_60s_theme";
        String second30 = "android.resource://" + getPackageName() + "/raw/thirty_second_song";
        String pixel = "android.resource://" + getPackageName() + "/raw/starchipp1";

        songList.add(new SongClass(batman, R.drawable.batman1966, "Batman 60's Theme", "junkboy187"));
        songList.add(new SongClass(second30, R.drawable.second_30, "Thirty Second Song", "soexria1504"));
        songList.add(new SongClass(pixel, R.drawable.pixel, "Star Chippet Theme", "gRaVy_TrAiN"));

        if (player == null) {

            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setOnPreparedListener(this);
            player.setOnCompletionListener(this);

            try {

                player.setDataSource(this, Uri.parse(songList.get(MusicService.this.musicPosition).getnSong()));


            } catch (Exception e) {
                e.printStackTrace();

                player.release();
                player = null;
            }
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return new aBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void playMusic() {

        if (player != null && !musicPrep) {

            player.prepareAsync();
            playWhenDone = true;

        } else if (player != null || !musicResumed) {

            musicResumed = true;
            player.seekTo(musicPosition);
            player.start();

//            duration = (player.getDuration() / 6000000);
//
//            Log.d(TAG, "" + duration);
//            totalDuration = "" + (player.getDuration() / 6000000);

            Log.d(TAG, "PLAYING");

        }

        if (player != null && player.isPlaying()) {

            musicResumed = false;
            player.pause();
            player.seekTo(musicPosition);

            Log.d(TAG, "PAUSE");

        }

    }

    public void stopMusic() {

        if (player != null && player.isPlaying()) {

            player.stop();
            musicPrep = false;

            Log.d(TAG, "STOP");

        }

    }

    public void backMusic() {

        musicPosition = (musicPosition - 1);

        try {

            player.reset();
            player.setDataSource(MusicService.this, Uri.parse(songList.get(musicPosition).getnSong()));
            player.prepareAsync();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public void forwardMusic() {

        musicPosition = (musicPosition + 1);

        try {

            player.reset();
            player.setDataSource(MusicService.this, Uri.parse(songList.get(musicPosition).getnSong()));
            player.prepareAsync();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    public void loopMusic() {

        if (player != null) {

            player.setLooping(true);

        }

    }


    public void shuffleMusic() {

        Random randomSong = new Random();

        musicPosition = randomSong.nextInt(3);

        try {

            player.reset();
            player.setDataSource(MusicService.this, Uri.parse(songList.get(musicPosition).getnSong()));
            player.prepareAsync();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        musicPrep = true;

        if (player != null && musicResumed) {
            player.seekTo(musicPosition);
            player.start();

            Log.d(TAG, "In Prep");
        }


        if (playWhenDone) {

            if (player != null) {

                player.start();

            }

        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

        musicPosition = (musicPosition + 1);

        try {

            playWhenDone = true;
            player.reset();
            player.setDataSource(MusicService.this, Uri.parse(songList.get(musicPosition).getnSong()));
            player.prepareAsync();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "DESTROY CALLED");
        player.release();
        player = null;
    }


}

