package com.fullsail.jketch.ketcham_josh_lab5;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MapLocationFrag extends MapFragment implements GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener, GoogleMap.InfoWindowAdapter {

    public static final String TAG = "MAP_FRAGMENT";

    GoogleMap gMap;
    Marker marker;
    ArrayList<MapInfo> theMapInfoArray;

    String stringTitle;
    String stringSnippet;
    String imageString;


    public MapLocationFrag() {
        // Required empty public constructor
    }

    public static MapLocationFrag newInstance() {
        MapLocationFrag fragment = new MapLocationFrag();

        return fragment;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        gMap = getMap();

        try {

            FileInputStream fis = getActivity().openFileInput("TheGeoData.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            theMapInfoArray = (ArrayList<MapInfo>) ois.readObject();
            ois.close();

            for (int i = 0; i < theMapInfoArray.size(); i++) {

                marker = gMap.addMarker(new MarkerOptions()
                                .title(theMapInfoArray.get(i).getTitle())
                                .snippet(theMapInfoArray.get(i).getSnippet())
                                .position(new LatLng(theMapInfoArray.get(i).getLat(), theMapInfoArray.get(i).getLong()))
//                        .icon((Bitmap) theMapInfoArray.get(i).getData())
                );

            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        gMap.setOnMapLongClickListener(this);
        gMap.setOnInfoWindowClickListener(this);
        gMap.setInfoWindowAdapter(this);

        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.593770, -81.303797), 14));

        gMap.setMyLocationEnabled(true);

        if (marker != null) {

            marker.getPosition();

        }
    }


    public void removeMarker() {

        theMapInfoArray = null;

        try {

            FileInputStream fis = getActivity().openFileInput("TheGeoData.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            theMapInfoArray = (ArrayList<MapInfo>) ois.readObject();

        } catch (Exception e) {

            e.printStackTrace();
        }

        if (theMapInfoArray != null) {

            for (int i = 0; i < theMapInfoArray.size(); i++) {

                MapInfo theData = theMapInfoArray.get(i);

                if (stringTitle.equals(theData.getTitle()) && stringTitle.equals(theData.getSnippet()) && stringTitle.equals(theData.getData()) &&
                        Double.compare(theData.getLat(), marker.getPosition().latitude) == 0 &&
                        Double.compare(theData.getLong(), marker.getPosition().longitude) == 0) {

                    theMapInfoArray.remove(i);

                    if (marker != null) {

                        marker.remove();

                    }

                    try {

                        FileOutputStream fos = getActivity().openFileOutput("TheGeoData.dat", Context.MODE_PRIVATE);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(theMapInfoArray);
                        oos.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

        }

    }


    @Override
    public void onMapLongClick(LatLng latLng) {

        marker = gMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("New Pin"));

        LatLng theLatLng = marker.getPosition();

        double lat = theLatLng.latitude;

        Log.d(TAG, "" + lat);

        double lng = theLatLng.longitude;


        Intent toFormIntent = new Intent(getActivity(), FormActivity.class);
        toFormIntent.putExtra(StringData.GEO_LOC_LAT, lat);
        toFormIntent.putExtra(StringData.GEO_LOC_LNG, lng);
        startActivity(toFormIntent);

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        Intent toDataIntent = new Intent(getActivity(), ViewDataActivity.class);
        toDataIntent.putExtra(StringData.FIELD_ONE, stringTitle);
        toDataIntent.putExtra(StringData.FIELD_TWO, stringSnippet);
        toDataIntent.putExtra(StringData.IMAGE_DATA, imageString);
        startActivity(toDataIntent);

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        View v = getActivity().getLayoutInflater().inflate(R.layout.marker_layout, null);

        if (theMapInfoArray != null) {

            for (int i = 0; i < theMapInfoArray.size(); i++) {

                MapInfo theInfo = theMapInfoArray.get(i);

                if (Double.compare(theInfo.getLat(), marker.getPosition().latitude) == 0 &&
                        Double.compare(theInfo.getLong(), marker.getPosition().longitude) == 0) {

                    BitmapFactory.Options bfo = new BitmapFactory.Options();
                    bfo.inSampleSize = 8;

                    imageString = theInfo.getData();

                    ImageView icon = (ImageView) v.findViewById(R.id.markerImage);
                    icon.setImageBitmap((Bitmap) BitmapFactory.decodeFile(imageString, bfo));

                    stringTitle = theInfo.getTitle();

                    TextView textTitle = (TextView) v.findViewById(R.id.theTitle);
                    textTitle.setText(stringTitle);

                    stringSnippet = theInfo.getSnippet();

                    TextView textSnippet = (TextView) v.findViewById(R.id.theSnippet);
                    textSnippet.setText(stringSnippet);

                }

            }

        }

        return v;

    }

}
