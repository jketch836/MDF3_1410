package com.fullsail.jketch.ketcham_josh_lab2_intentservice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;


public class ImageBaseAdapter extends BaseAdapter {

    protected Context mContext;

    private File[] files;

    public ImageBaseAdapter(Context c, File[] file) {
        mContext = c;
        files = file;
    }


    @Override
    public int getCount() {
        return files.length;
    }

    @Override
    public Object getItem(int position) {
        return files[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView anImageView;

        if (convertView == null) {

            anImageView = new ImageView(mContext);

            GridView.LayoutParams gvlp = new GridView.LayoutParams(500, 500);

            anImageView.setLayoutParams(gvlp);

            String filePostition = files[position].toString();

            BitmapFactory.Options bfo = new BitmapFactory.Options();
            bfo.inSampleSize = 4;

            anImageView.setImageBitmap((Bitmap) BitmapFactory.decodeFile(filePostition, bfo));

        } else {

            anImageView = (ImageView) convertView;

        }



        return anImageView;
    }
}
