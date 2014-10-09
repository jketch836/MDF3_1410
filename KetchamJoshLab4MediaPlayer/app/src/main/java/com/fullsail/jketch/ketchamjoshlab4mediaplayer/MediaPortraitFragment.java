package com.fullsail.jketch.ketchamjoshlab4mediaplayer;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


public class MediaPortraitFragment extends Fragment implements View.OnClickListener, ServiceConnection{

    public static final String TAG = "Media_Portrait_Frag";

    MediaListener mListener;

    SeekBar seekBar;

    MusicService mediaService;

    ServiceConnection sConnection;

    Intent musicIntent;

    boolean musicBool;

    MusicService ms = new MusicService();

    public static MediaPortraitFragment newInstance() {
        MediaPortraitFragment fragment = new MediaPortraitFragment();

        return fragment;
    }

    public MediaPortraitFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (MediaListener) activity;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_media_portrait, container, false);

        ((Button) view.findViewById(R.id.previousButton)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.startPauseButton)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.stopButton)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.forwardButton)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.loopButton)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.shuffleButton)).setOnClickListener(this);

        seekBar = (SeekBar) view.findViewById(R.id.theBar);

        musicIntent = new Intent(view.getContext(), MusicService.class);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        musicBool = false;

        getActivity().bindService(musicIntent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onPause() {
        super.onPause();

        musicBool = false;

    }

    public void onServiceConnected(ComponentName name, IBinder service) {
        MusicService.aBinder binder = (MusicService.aBinder) service;

        mediaService = binder.getService();

        musicBool = true;
    }

    public void onServiceDisconnected(ComponentName name) {
        musicBool = false;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.previousButton) {

            mediaService.backMusic();

        } else if (id == R.id.startPauseButton) {

            mediaService.playMusic();

            seekBar.setMax(ms.duration);

            ((TextView) getActivity().findViewById(R.id.total)).setText(ms.totalDuration);

        } else if (id == R.id.stopButton) {

            mediaService.stopMusic();

        } else if (id == R.id.forwardButton) {

            mediaService.forwardMusic();

        } else if (id == R.id.loopButton) {

            mediaService.loopMusic();

        } else if (id == R.id.shuffleButton) {

            mediaService.shuffleMusic();

        }

    }

}
