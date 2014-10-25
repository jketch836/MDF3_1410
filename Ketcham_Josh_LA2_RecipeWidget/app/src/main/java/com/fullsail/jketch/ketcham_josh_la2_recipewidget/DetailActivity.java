package com.fullsail.jketch.ketcham_josh_la2_recipewidget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class DetailActivity extends Activity {


    String imageString;
    String rName;
    String rRating;
    StringBuilder rIngerdients = new StringBuilder();
String theIngrediants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle b = getIntent().getExtras();

        imageString = b.getString(StringClass.TOLISTIMAGE);
        rName = b.getString(StringClass.TOLISTNAME);
        rRating = b.getString(StringClass.TOLISTRATING);

            String ingrediants = rIngerdients.append(b.getString(StringClass.TOLISTINGREDIENTS)).toString();


        SmartImageView siv = (SmartImageView) findViewById(R.id.recipeImage);
        siv.setImageUrl(imageString);

        TextView rNameText = (TextView) findViewById(R.id.recipeName);
        rNameText.setText(rName);

        TextView rRatingText = (TextView) findViewById(R.id.recipeRating);
        rRatingText.setText(rRating);

        TextView rIngredients = (TextView) findViewById(R.id.recipeIngredients);
        rIngredients.setText(ingrediants);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {


            ArrayList<RecipeClass> recipeArray = null;

            try {

                FileInputStream fis = this.openFileInput("FavoriteRecipes.dat");
                ObjectInputStream ois = new ObjectInputStream(fis);
                recipeArray = (ArrayList<RecipeClass>) ois.readObject();
                ois.close();

            } catch (Exception e) {

                e.printStackTrace();
            }

            if (recipeArray == null) {

                recipeArray = new ArrayList<RecipeClass>();

            }

            recipeArray.add(new RecipeClass(rName, imageString, rIngerdients, rRating));

            try {

                FileOutputStream fos = this.openFileOutput("FavoriteRecipes.dat", Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(recipeArray);
                oos.close();

                Toast.makeText(this, "Recipe Saved to Favorites", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }


            return true;
        }
        return super.onOptionsItemSelected(item);

    }



}
