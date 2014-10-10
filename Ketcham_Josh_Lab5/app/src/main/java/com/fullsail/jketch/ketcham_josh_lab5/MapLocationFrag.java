package com.fullsail.jketch.ketcham_josh_lab5;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MapLocationFrag extends MapFragment implements GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener, GoogleMap.InfoWindowAdapter {

    public static final String TAG = "MAP_FRAGMENT";

    GoogleMap gMap;
    Marker marker;
    ArrayList<MapInfo> theMapInfoArray;

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

        theMapInfoArray = null;

        gMap.setOnMapLongClickListener(this);
        gMap.setOnInfoWindowClickListener(this);
        gMap.setInfoWindowAdapter(this);

//        double latitude = getActivity().get

        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.593770, -81.303797), 14));

        gMap.setMyLocationEnabled(true);

        if (marker != null){

            marker.getPosition();

        }
    }

    public void removeMarker() {

//        if (theMapInfoArray != null) {
//
//            for (int i = 0; i <theMapInfoArray.size(); i++) {
//
//                MapInfo mapData = theMapInfoArray.get(i);
//
//                String
//
//                if () {
//
//                    theMapInfoArray.remove(i);
//
//                    try {
//
//                        FileOutputStream fos = getActivity().openFileOutput("FormData.dat", Context.MODE_PRIVATE);
//                        ObjectOutputStream oos = new ObjectOutputStream(fos);
//                        oos.writeObject(theMapInfoArray);
//                        oos.close();
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    break;
//                }
//
//            }
//        }

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        marker = gMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("New Pin"));

        LatLng theLatLng = marker.getPosition();

        double lat = theLatLng.latitude;
        double lng = theLatLng.longitude;


        Intent toFormIntent = new Intent(getActivity(), FormActivity.class);
        toFormIntent.putExtra(StringData.GEO_LOC_LAT, lat);
        toFormIntent.putExtra(StringData.GEO_LOC_LNG, lng);
        startActivity(toFormIntent);

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        Intent toDataIntent = new Intent(getActivity(), ViewDataActivity.class);
//        toDataIntent.putExtra(StringData.FIELD_ONE, );
//        toDataIntent.putExtra(StringData.FIELD_TWO, );
        startActivity(toDataIntent);

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        View v = getActivity().getLayoutInflater().inflate(R.layout.marker_layout, null);

        try {

            FileInputStream fis = getActivity().openFileInput("TheGeoData.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            theMapInfoArray = (ArrayList<MapInfo>) ois.readObject();

        } catch (Exception e) {

            e.printStackTrace();
        }

        if (theMapInfoArray != null) {

            for (int i = 0; i < theMapInfoArray.size(); i++) {

                MapInfo theInfo = theMapInfoArray.get(i);

                ((TextView) v.findViewById(R.id.theTitle)).setText(theInfo.getTitle());
                ((TextView) v.findViewById(R.id.theSnippet)).setText(theInfo.getSnippet());

            }

        }

        return v;
    }

}
