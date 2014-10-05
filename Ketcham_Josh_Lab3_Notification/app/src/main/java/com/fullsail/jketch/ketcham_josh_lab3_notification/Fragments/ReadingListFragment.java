package com.fullsail.jketch.ketcham_josh_lab3_notification.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fullsail.jketch.ketcham_josh_lab3_notification.NewsInfo;
import com.fullsail.jketch.ketcham_josh_lab3_notification.R;
import com.fullsail.jketch.ketcham_josh_lab3_notification.TheStrings;
import com.fullsail.jketch.ketcham_josh_lab3_notification.theBaseAdapter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class ReadingListFragment extends Fragment {

    public static final String TAG = "READING_LIST_FRAGMENT";


    aInterface InterfaceListener;
    ArrayList<String> newsArray;
    ListView aListView;

    public static ReadingListFragment newInstance() {
        ReadingListFragment fragment = new ReadingListFragment();

        return fragment;
    }

    public ReadingListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            InterfaceListener = (aInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
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

        View v = inflater.inflate(R.layout.fragment_reading_list, container, false);

        aListView = (ListView) v.findViewById(R.id.listView);

        try {

            FileInputStream fis = getActivity().openFileInput("ListData.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            newsArray = (ArrayList<String>) ois.readObject();

        } catch (Exception e) {

            e.printStackTrace();
        }

        if (newsArray == null) {

            newsArray = new ArrayList<String>();

        }

        theBaseAdapter baseAdapter = new theBaseAdapter(getActivity(), newsArray);

        aListView.setAdapter(baseAdapter);

        aListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                NewsInfo newsinfo = (NewsInfo) parent.getAdapter().getItem(position);

                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(newsinfo.getURL()));
                startActivity(browserIntent);

            }
        });

        return v;

    }


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle b = intent.getExtras();

            if (intent.getAction().equals(TheStrings.ACTION_SAVE)) {

                String title = b.getString(TheStrings.TITLE);
                String urlString = b.getString(TheStrings.URL);
                String summaryString = b.getString(TheStrings.SUMMARY);

                NewsInfo newsInfo = new NewsInfo(summaryString, title, urlString);

                ArrayList<NewsInfo> theNewsArray = null;

                try {

                    FileInputStream fis = context.openFileInput("ListData.dat");
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    theNewsArray = (ArrayList<NewsInfo>) ois.readObject();

                } catch (Exception e) {

                    e.printStackTrace();
                }

                if (theNewsArray == null) {

                    theNewsArray = new ArrayList<NewsInfo>();

                }

                theNewsArray.add(newsInfo);

                try {

                    FileOutputStream fos = context.openFileOutput("ListData.dat", Context.MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(theNewsArray);
                    oos.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
    };

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(TheStrings.ACTION_SAVE);
        getActivity().registerReceiver(broadcastReceiver, filter);
    }


    @Override
    public void onPause() {
        super.onPause();

        getActivity().unregisterReceiver(broadcastReceiver);

    }
}
