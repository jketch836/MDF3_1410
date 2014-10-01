package com.fullsail.jketch.ketcham_josh_lab1_broadcastreceiver_project.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fullsail.jketch.ketcham_josh_lab1_broadcastreceiver_project.R;


public class FormFragment extends Fragment {

    public static final String TAG = "FORM_FRAG";

    public static final String FIRST_NAME = "com.fullsail.android.EXTRA_FIRST_NAME";
    public static final String LAST_NAME = "com.fullsail.android.EXTRA_LAST_NAME";
    public static final String AGE = "com.fullsail.android.EXTRA_AGE";

    InfoInterface InfoListener;

    public FormFragment() {
        // Required empty public constructor
    }

    public static FormFragment newInstance() {
        FormFragment fragment = new FormFragment();

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

        final View view = inflater.inflate(R.layout.fragment_form, container, false);

        final EditText firstName = (EditText) view.findViewById(R.id.firstNameField);
        final EditText lastName = (EditText) view.findViewById(R.id.lastNameField);
        final EditText ageText = (EditText) view.findViewById(R.id.ageField);


        ((Button) view.findViewById(R.id.EnterButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firstName.getText().length() != 0 && lastName.getText().length() != 0 && ageText.getText().length() != 0) {

                    Bundle bundle = new Bundle();
                    bundle.putString(FIRST_NAME, firstName.getText().toString());
                    bundle.putString(LAST_NAME, lastName.getText().toString());
                    bundle.putString(AGE, ageText.getText().toString());

                    Intent intent = new Intent(getActivity(), ListFragment.class);
                    intent.putExtras(bundle);
//                    sendBroadcast(intent);
                    startActivity(intent);

                } else {

                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setMessage("Please Populate all the Fields Provided");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alert.show();

                }

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
