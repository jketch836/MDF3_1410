package com.fullsail.jketch.sqlitedatabase_lab8_ketcham_josh.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fullsail.jketch.sqlitedatabase_lab8_ketcham_josh.DetailActivity;
import com.fullsail.jketch.sqlitedatabase_lab8_ketcham_josh.EmployeeAdapter;
import com.fullsail.jketch.sqlitedatabase_lab8_ketcham_josh.R;
import com.fullsail.jketch.sqlitedatabase_lab8_ketcham_josh.SQLDataBaseHelper;
import com.fullsail.jketch.sqlitedatabase_lab8_ketcham_josh.StringsClass;

/**
 * Created by jketch on 10/21/14.
 */
public class ListViewFragment extends Fragment {

    public static final String TAG = "ListViewFragment.TAG";

    TheInterface mListener;

    EmployeeAdapter adapter;
    ListView listView;
    Cursor c;

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
            mListener = (TheInterface) activity;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list_view, container, false);

        listView = (ListView) v.findViewById(R.id.listView);

        SQLDataBaseHelper db = new SQLDataBaseHelper(getActivity());

        c = db.getAllRecords();

        if (adapter == null) {

            adapter = new EmployeeAdapter(getActivity(), c);

        }

        return v;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (adapter != null) {

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                    Cursor employeeCursor = (Cursor) parent.getAdapter().getItem(i);

                    Intent intent = new Intent(getActivity(), DetailActivity.class);


                    intent.putExtra(StringsClass.FIRST_NAME, employeeCursor.getString(1));
                    intent.putExtra(StringsClass.LAST_NAME, employeeCursor.getString(2));
                    intent.putExtra(StringsClass.HIRE_DATE, employeeCursor.getString(3));
                    intent.putExtra(StringsClass.HOUR_PAY, employeeCursor.getString(4));
                    intent.putExtra(StringsClass.PROFILE_IMAGE, employeeCursor.getString(5));
                    intent.putExtra(StringsClass.ROW_ID, i);

                    getActivity().startActivityForResult(intent, 1);

                }
            });

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        refreshList();

    }



    public void refreshList() {

        SQLDataBaseHelper dbh = new SQLDataBaseHelper(getActivity());

        Cursor c = dbh.getAllRecords();

        adapter.swapCursor(c);

        adapter.notifyDataSetChanged();

    }



}
