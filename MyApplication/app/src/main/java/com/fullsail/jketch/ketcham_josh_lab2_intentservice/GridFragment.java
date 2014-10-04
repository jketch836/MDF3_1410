package com.fullsail.jketch.ketcham_josh_lab2_intentservice;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;


public class GridFragment extends Fragment {

    public static final String TAG = "GRID_VIEW_FRAGMENT";

    public static final String UPDATE_IMAGES = "com.fullsail.android.UPDATE_IMAGE";

    ImageBaseAdapter imageAdapter;
    GridView theGridView;
    File[] fileArray;

    public interface GridFragListener {
        public void GridFragInteraction();
    }

    GridFragListener gridListener;


    public static GridFragment newInstance() {
        GridFragment fragment = new GridFragment();

        return fragment;
    }


    public GridFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            gridListener = (GridFragListener) activity;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(activity.toString()
                    + " must implement GridFragListener");
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

        return inflater.inflate(R.layout.fragment_grid, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        theGridView = (GridView) getActivity().findViewById(R.id.gridView);

        File es = getActivity().getExternalFilesDir(null);


        if (es != null) {

            fileArray = es.listFiles();

            imageAdapter = new ImageBaseAdapter(getActivity(), fileArray);

            theGridView.setAdapter(imageAdapter);

            theGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Log.d(TAG, "" + position);

                    Intent viewImageIntent = new Intent(Intent.ACTION_VIEW);
                    viewImageIntent.setDataAndType(Uri.fromFile(fileArray[position]), "image/jpeg");
                    startActivity(Intent.createChooser(viewImageIntent, ""));

                }
            });

        }
    }


    BroadcastReceiver bReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(UPDATE_IMAGES)) {

                File es = getActivity().getExternalFilesDir(null);

                if (es != null) {

                    fileArray = es.listFiles();

                    imageAdapter.notifyDataSetChanged();

                }

            }

        }
    };


    @Override
    public void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();

        getActivity().registerReceiver(bReceiver, filter);
    }


    @Override
    public void onPause() {
        super.onPause();

        getActivity().unregisterReceiver(bReceiver);

    }
}
