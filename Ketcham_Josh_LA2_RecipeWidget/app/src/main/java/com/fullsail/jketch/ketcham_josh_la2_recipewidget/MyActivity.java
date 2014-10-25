package com.fullsail.jketch.ketcham_josh_la2_recipewidget;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.fullsail.jketch.ketcham_josh_la2_recipewidget.Fragments.FavoritesFragment;
import com.fullsail.jketch.ketcham_josh_la2_recipewidget.Fragments.ListFrag;
import com.fullsail.jketch.ketcham_josh_la2_recipewidget.Fragments.TheInterface;


public class MyActivity extends Activity implements ActionBar.OnNavigationListener, TheInterface {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<String>(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        getResources().getStringArray(R.array.categories)),
                this);

        getFragmentManager().beginTransaction().replace(R.id.frameLayout, ListFrag.newInstance(), ListFrag.TAG);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getActionBar().getSelectedNavigationIndex());
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.
        if (position == 0){

            getFragmentManager().beginTransaction().replace(R.id.frameLayout, ListFrag.newInstance()).commit();

        } else if (position == 1) {

            getFragmentManager().beginTransaction().replace(R.id.frameLayout, FavoritesFragment.newInstance()).commit();

        }
        return true;
    }

    @Override
    public void getInfo() {

    }

}
