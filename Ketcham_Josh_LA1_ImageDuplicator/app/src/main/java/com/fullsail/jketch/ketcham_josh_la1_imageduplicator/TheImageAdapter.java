package com.fullsail.jketch.ketcham_josh_la1_imageduplicator;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;


public class TheImageAdapter extends BaseAdapter {

    protected Context mContext;

    private File[] theFilesArray;

    public TheImageAdapter(Context c, File[] file) {
        mContext = c;
        theFilesArray = file;
    }


    @Override
    public int getCount() {
        return theFilesArray.length;
    }

    @Override
    public Object getItem(int position) {
        return theFilesArray[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView theImageView;

        if (convertView == null) {

            theImageView = new ImageView(mContext);

            GridView.LayoutParams gvlp = new GridView.LayoutParams(500, 500);

            theImageView.setLayoutParams(gvlp);

            String filePostition = theFilesArray[position].toString();

            BitmapFactory.Options bfo = new BitmapFactory.Options();
            bfo.inSampleSize = 10;

            theImageView.setImageBitmap(BitmapFactory.decodeFile(filePostition));

        } else {

            theImageView = (ImageView) convertView;

        }

        return theImageView;
    }

}
