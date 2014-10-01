package com.fullsail.jketch.ketcham_josh_lab1_broadcastreceiver_project.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fullsail.jketch.ketcham_josh_lab1_broadcastreceiver_project.MainActivity;
import com.fullsail.jketch.ketcham_josh_lab1_broadcastreceiver_project.R;

public class DetailsFragment extends Fragment {

    public static final String TAG = "DETAIL_FRAG";

    public static final String FIRST_NAME = "com.fullsail.android.EXTRA_FIRST_NAME";
    public static final String LAST_NAME = "com.fullsail.android.EXTRA_LAST_NAME";
    public static final String AGE = "com.fullsail.android.EXTRA_AGE";

    InfoInterface InfoListener;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance() {
        DetailsFragment fragment = new DetailsFragment();

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

        final View view = inflater.inflate(R.layout.fragment_details, container, false);

        Bundle newBundle = getActivity().getIntent().getExtras();

        final EditText firstName = (EditText) view.findViewById(R.id.detailFirstNameField);
        final EditText lastName = (EditText) view.findViewById(R.id.detailLastNameField);
        final EditText ageText = (EditText) view.findViewById(R.id.detailAgeField);

        String fName = newBundle.getString(MainActivity.FIRST_NAME);
        String lName = newBundle.getString(MainActivity.LAST_NAME);
        String theAge = newBundle.getString(MainActivity.AGE);


        firstName.setText(fName);
        lastName.setText(lName);
        ageText.setText(theAge);

        ((Button) view.findViewById(R.id.DeleteButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListViewFragment.adapter.remove(ListViewFragment.adapter.getItem(ListViewFragment.thePosition));

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
