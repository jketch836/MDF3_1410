package com.fullsail.jketch.ketcham_josh_lab3_notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jketch on 10/4/14.
 */
public class theBaseAdapter extends BaseAdapter {

   Context mContext;
    ArrayList<String> arrayList;

    public theBaseAdapter (Context c, ArrayList<String> list) {

        mContext = c;
        arrayList = list;

    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.layout, parent, false);

        }

        NewsInfo newsData = (NewsInfo) getItem(position);

        ((TextView) convertView.findViewById(R.id.title)).setText(newsData.getTitle());


        return convertView;
    }
}
