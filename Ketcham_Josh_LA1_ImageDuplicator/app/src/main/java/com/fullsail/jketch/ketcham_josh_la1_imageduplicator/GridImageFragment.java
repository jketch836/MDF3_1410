package com.fullsail.jketch.ketcham_josh_la1_imageduplicator;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;


public class GridImageFragment extends Fragment implements ActionBar.OnNavigationListener {

    public static final String TAG = "Grid_Image_Fragment";

    TheImageAdapter imageAdapter;
    File[] theFileArray;
    private ActionMode mActionMode;
    public int mImageSelected = -1;

    IntentServiceClass theService = new IntentServiceClass();

    public interface GridFragmentListener {
        public void onFragmentInteraction();
    }

    private GridFragmentListener gridListener;

    public GridImageFragment() {
        // Required empty public constructor
    }

    public static GridImageFragment newInstance() {
        GridImageFragment fragment = new GridImageFragment();

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            gridListener = (GridFragmentListener) activity;
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
        return inflater.inflate(R.layout.fragment_grid_image, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GridView gridView = (GridView) getActivity().findViewById(R.id.theGridView);

        File es = getActivity().getExternalFilesDir(null);

        if (es != null) {

            theFileArray = es.listFiles();

            imageAdapter = new TheImageAdapter(getActivity(), theFileArray);

            gridView.setAdapter(imageAdapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Log.d(TAG, "" + position);

                    Intent viewPicIntent = new Intent(Intent.ACTION_VIEW);
                    viewPicIntent.setDataAndType(Uri.fromFile(theFileArray[position]), "image/jpeg");
                    startActivity(viewPicIntent);

                }
            });

        }

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if (mActionMode != null) {
                    return false;
                }
                mImageSelected = position;

                mActionMode = getActivity().startActionMode(mActionModeCallback);

                return true;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(StringsClass.IMAGE_PATH);
        filter.addAction(StringsClass.ACTION_DELETE);
        getActivity().registerReceiver(theService.theReceiver, filter);

    }

    @Override
    public void onPause() {
        super.onPause();

        getActivity().unregisterReceiver(theService.theReceiver);

    }

    @Override
    public void onDetach() {
        super.onDetach();

        gridListener = null;

    }


    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:

                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("HOLD ON!!");
                    dialog.setMessage("Are you sure you want to Delete this Picture?");
                    dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent toDelete = new Intent(StringsClass.ACTION_DELETE);
                            toDelete.putExtra(StringsClass.DELETE_FILE, theFileArray[mImageSelected].getAbsoluteFile());
                            getActivity().sendBroadcast(toDelete);

                        }
                    });
                    dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();

                    mode.finish();
                    return true;

                case R.id.action_share:

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, theFileArray[mImageSelected].getAbsoluteFile());
                    shareIntent.setType("image/jpeg");
                    startActivity(shareIntent);

                    mode.finish();
                    return true;

                default:

                    return false;

            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        return false;
    }

}
