package com.fullsail.jketch.ketcham_josh_la2_recipewidget.Widget3x2;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.fullsail.jketch.ketcham_josh_la2_recipewidget.R;
import com.fullsail.jketch.ketcham_josh_la2_recipewidget.RecipeClass;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by jketch on 10/23/14.
 */
public class Widget32Factory implements RemoteViewsService.RemoteViewsFactory {

    public static final String FORWARD_ACTION_2 = "com.fullsail.android.collectionwidgetdemo.FORWARD_ACTION_2";
    public static final String PREVIOUS_ACTION_2 = "com.fullsail.android.collectionwidgetdemo.PREVIOUS_ACTION_2";

    private static final int ID_CONSTANT = 0x0202020;

    private Context mContext;
    private ArrayList<RecipeClass> mRecipeList;

    public Widget32Factory(Context context){

        mContext = context;
        mRecipeList = new ArrayList<RecipeClass>();

    }

    @Override
    public void onCreate() {

        readTheData();

    }

    @Override
    public void onDataSetChanged() {

        readTheData();

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mRecipeList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews rvs = new RemoteViews(mContext.getPackageName(), R.layout.widget32_item_layout);

        rvs.setOnClickPendingIntent(R.id.nextBTN32, getPendingIntent(mContext, FORWARD_ACTION_2));
        rvs.setOnClickPendingIntent(R.id.previousBTN32, getPendingIntent(mContext, PREVIOUS_ACTION_2));

        rvs.setTextViewText(R.id.rNameTextWidget32, mRecipeList.get(position).getName());
        rvs.setImageViewBitmap(R.id.recipeImageWidget32, getBitmap(mRecipeList.get(position).getImage()));

        return rvs;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return ID_CONSTANT + position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    protected void readTheData (){

        SharedPreferences selectedPreference = PreferenceManager.getDefaultSharedPreferences(mContext);
        String theSelectedPref = selectedPreference.getString("PREF_CATEGORY", "NOT_ASSIGNED");

        if (theSelectedPref.equals("1")) {

            try {

                FileInputStream fis = mContext.openFileInput("DownloadedRecipes.dat");
                ObjectInputStream ois = new ObjectInputStream(fis);
                mRecipeList = (ArrayList<RecipeClass>) ois.readObject();
                ois.close();

            } catch (Exception e) {

                e.printStackTrace();
            }

        } else if (theSelectedPref.equals("2")) {

            try {

                FileInputStream fis = mContext.openFileInput("FavoriteRecipes.dat");
                ObjectInputStream ois = new ObjectInputStream(fis);
                mRecipeList = (ArrayList<RecipeClass>) ois.readObject();
                ois.close();

            } catch (Exception e) {

                e.printStackTrace();
            }

        }

    }

    public Bitmap getBitmap(String stringURL) {

        Bitmap bitmap = null;

        try {

            URL url = new URL(stringURL);

            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        } catch (Exception e) {

            e.printStackTrace();

        }

        return bitmap;

    }

    protected PendingIntent getPendingIntent(Context context, String action) {
        Intent intent = new Intent(context, Widget32Provider.class);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

}
