package com.fullsail.jketch.ketchamjoshlab4mediaplayer;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MediaLandscapeFragment extends Fragment {

    public static final String TAG = "Media_Land_Frag";

    MediaListener mListener;

    public static MediaLandscapeFragment newInstance() {
        MediaLandscapeFragment fragment = new MediaLandscapeFragment();

        return fragment;
    }

    public MediaLandscapeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_media_portrait, container, false);
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

    public interface MediaListener {
        public void MediaListenerFunc();
    }
}
