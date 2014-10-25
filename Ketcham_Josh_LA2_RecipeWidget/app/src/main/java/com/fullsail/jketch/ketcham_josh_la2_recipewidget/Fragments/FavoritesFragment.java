package com.fullsail.jketch.ketcham_josh_la2_recipewidget.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fullsail.jketch.ketcham_josh_la2_recipewidget.R;
import com.fullsail.jketch.ketcham_josh_la2_recipewidget.RecipeClass;
import com.fullsail.jketch.ketcham_josh_la2_recipewidget.RecipeListAdapter;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;


public class FavoritesFragment extends Fragment {

    public static final String TAG = "FAV_FRAG";
    ArrayList<RecipeClass> arrayList;

    TheInterface mListener;

    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();

        return fragment;
    }

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (TheInterface) activity;
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
        View v = inflater.inflate(R.layout.fragment_list, container, false);


        return v;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (arrayList != null) {

            try {

                FileInputStream fis = getActivity().openFileInput("FavoriteRecipes.dat");
                ObjectInputStream ois = new ObjectInputStream(fis);
                arrayList = (ArrayList<RecipeClass>) ois.readObject();
                ois.close();

            } catch (Exception e) {

                e.printStackTrace();
            }


            ListView lv = (ListView) getActivity().findViewById(R.id.listView);

            RecipeListAdapter rla = new RecipeListAdapter(getActivity(), arrayList);

            lv.setAdapter(rla);

        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
