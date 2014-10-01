package com.fullsail.jketch.ketcham_josh_lab1_broadcastreceiver_project.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fullsail.jketch.ketcham_josh_lab1_broadcastreceiver_project.DetailActivity;
import com.fullsail.jketch.ketcham_josh_lab1_broadcastreceiver_project.MainActivity;
import com.fullsail.jketch.ketcham_josh_lab1_broadcastreceiver_project.R;

import java.util.ArrayList;


public class ListViewFragment extends Fragment {

public static final String TAG = "LISTVIEW_FRAG";

    public static final String UPDATE_LIST = "com.fullsail.android.ACTION_UPDATE_LIST";

    InfoInterface InfoListener;

    ArrayList<String> arrayList;
    Bundle personInfo;

    public static ArrayAdapter<String> adapter;
    public static int thePosition = -1;

    public ListViewFragment() {
        // Required empty public constructor
    }

    public static ListViewFragment newInstance() {
        ListViewFragment fragment = new ListViewFragment();

        return fragment;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            InfoListener = (InfoInterface) activity;
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

        View view = inflater.inflate(R.layout.fragment_list_view, container, false);

        personInfo = getActivity().getIntent().getExtras();

        getActivity().registerReceiver(receiver, null);

        arrayList = new ArrayList<String>();

        ListView listView = (ListView) view.findViewById(R.id.theListView);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                thePosition = position;

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtras(personInfo);
                startActivity(intent);


            }
        });

        return view;
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(UPDATE_LIST)) {

                if (intent.hasExtra(MainActivity.FIRST_NAME) && intent.hasExtra(MainActivity.LAST_NAME) && intent.hasExtra(MainActivity.AGE)) {

//                    try {
//
//                        FileInputStream fin = getActivity().openFileInput( + ".dat");
//                        ObjectInputStream oin = new ObjectInputStream(fin);
//                        FormData form = (FormData) oin.readObject();
//
//                        oin.close();
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                    String pFirstName = personInfo.getString(MainActivity.FIRST_NAME);
                    String pLastName = personInfo.getString(MainActivity.LAST_NAME);

                    arrayList.add(pFirstName + " " + pLastName);

                    adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayList);

                }

            }

        }
    };

}
