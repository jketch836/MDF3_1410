package com.fullsail.jketch.ketcham_josh_collectionwidget_lab7.WidgetPackage;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.fullsail.jketch.ketcham_josh_collectionwidget_lab7.PersonInfoClass;
import com.fullsail.jketch.ketcham_josh_collectionwidget_lab7.R;
import com.fullsail.jketch.ketcham_josh_collectionwidget_lab7.StringsClass;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by jketch on 10/16/14.
 */
public class CollectionFactory implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<PersonInfoClass> theContentList;
    private Context theContext;

    public CollectionFactory(Context c) {

        theContext = c;

        theContentList = null;

    }

    @Override
    public void onCreate() {

        try {

            FileInputStream fis = theContext.openFileInput("TheFormData.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            theContentList = (ArrayList<PersonInfoClass>) ois.readObject();
            ois.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        if (theContentList == null) {

            theContentList = new ArrayList<PersonInfoClass>();
        }

    }

    @Override
    public void onDataSetChanged() {

//        int appWidIDs[] = awm.getAppWidgetIds(new ComponentName(theContext, CollectionWidgetProvider.class.getName()));
//        awm.notifyAppWidgetViewDataChanged(appWidIDs, R.id.pInfo_list);

        if (theContentList != null) {

            try {

                FileInputStream fis = theContext.openFileInput("TheFormData.dat");
                ObjectInputStream ois = new ObjectInputStream(fis);
                theContentList = (ArrayList<PersonInfoClass>) ois.readObject();
                ois.close();

            } catch (Exception e) {

                e.printStackTrace();
            }

        }

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return theContentList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {

        RemoteViews rvs = new RemoteViews(theContext.getPackageName(), R.layout.list_info_layout);

        rvs.setTextViewText(R.id.name, theContentList.get(i).toString());

        Intent intent = new Intent();
        intent.putExtra(StringsClass.FIRST_NAME, theContentList.get(i).getfName());
        intent.putExtra(StringsClass.LAST_NAME, theContentList.get(i).getlName());
        intent.putExtra(StringsClass.AGE, theContentList.get(i).getAge());
        rvs.setOnClickFillInIntent(R.id.person_item, intent);

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
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
