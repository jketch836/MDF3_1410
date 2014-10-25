package com.fullsail.jketch.ketcham_josh_la2_recipewidget;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

/**
 * Created by jketch on 10/20/14.
 */
public class RecipeListAdapter extends BaseAdapter {

    private static final long ID_CONSTANT = 0x00001;
    Context mContext;
    ArrayList<RecipeClass> rArrayList;

    public RecipeListAdapter (Context c, ArrayList<RecipeClass> list) {

        mContext = c;
        rArrayList = list;

    }

    @Override
    public int getCount() {
        return rArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return rArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return ID_CONSTANT + i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.recipe_list_adapter, parent, false);

        }

        final RecipeClass rData = (RecipeClass) getItem(position);

        SmartImageView siv = (SmartImageView) convertView.findViewById(R.id.theRecipeImage);
        siv.setImageUrl(rData.getImage());

        ((TextView) convertView.findViewById(R.id.theName)).setText(rData.getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toList = new Intent(mContext, DetailActivity.class);

                toList.putExtra(StringClass.TOLISTIMAGE, rData.getImage());
                toList.putExtra(StringClass.TOLISTNAME, rData.getName());
                toList.putExtra(StringClass.TOLISTRATING, rData.getRating());
                toList.putExtra(StringClass.TOLISTINGREDIENTS, (java.io.Serializable) rData.getIngrediants());

                mContext.startActivity(toList);

            }
        });

        return convertView;
    }


}
