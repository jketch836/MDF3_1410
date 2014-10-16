package com.fullsail.jketch.ketcham_josh_collectionwidget_lab7.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fullsail.jketch.ketcham_josh_collectionwidget_lab7.PersonInfoClass;
import com.fullsail.jketch.ketcham_josh_collectionwidget_lab7.R;
import com.fullsail.jketch.ketcham_josh_collectionwidget_lab7.StringsClass;
import com.fullsail.jketch.ketcham_josh_collectionwidget_lab7.ViewActivity;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;


public class ListViewFragment extends Fragment {

    public static final String TAG = "LIST_VIEW_FRAGMENT";

    private theInterface mListener;

    ArrayList<PersonInfoClass> arrayList;

    ListView listView;

    ArrayAdapter<PersonInfoClass> personAdapter;


    public static ListViewFragment newInstance() {
        ListViewFragment fragment = new ListViewFragment();

        return fragment;
    }

    public ListViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (theInterface) activity;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);

        listView = (ListView) view.findViewById(R.id.listView);


        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        if (arrayList != null) {
//
//            personAdapter.notifyDataSetChanged();

        try {

            FileInputStream fis = getActivity().openFileInput("TheFormData.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            arrayList = (ArrayList<PersonInfoClass>) ois.readObject();
            ois.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        if (arrayList == null) {
            arrayList = new ArrayList<PersonInfoClass>();
        }


        personAdapter = new ArrayAdapter<PersonInfoClass>(getActivity(), android.R.layout.simple_list_item_1, arrayList);

        listView.setAdapter(personAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PersonInfoClass personData = (PersonInfoClass) parent.getAdapter().getItem(position);

                Intent intent = new Intent(getActivity(), ViewActivity.class);
                intent.putExtra(StringsClass.FIRST_NAME, personData.getfName());
                intent.putExtra(StringsClass.LAST_NAME, personData.getlName());
                intent.putExtra(StringsClass.AGE, personData.getAge());
                startActivity(intent);

            }
        });

//        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
