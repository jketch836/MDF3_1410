package com.fullsail.jketch.ketcham_josh_la2_recipewidget.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fullsail.jketch.ketcham_josh_la2_recipewidget.R;
import com.fullsail.jketch.ketcham_josh_la2_recipewidget.RecipeClass;
import com.fullsail.jketch.ketcham_josh_la2_recipewidget.RecipeListAdapter;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//import com.fullsail.jketch.ketcham_josh_la2_recipewidget.TheRecipeTask;


public class ListFrag extends Fragment {

    public static final String TAG = "LIST_FRAG";

    ArrayList<RecipeClass> foodData = new ArrayList<RecipeClass>();
    public ListView lv;

    TheInterface mListener;

    public static ListFrag newInstance() {
        ListFrag fragment = new ListFrag();

        return fragment;
    }

    public ListFrag() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof TheInterface) {

            mListener = (TheInterface) activity;

        } else {

            throw new IllegalArgumentException(activity.toString() + " must implement The Interface");

        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TheRecipeTask recipeTask = new TheRecipeTask(getActivity());

        recipeTask.execute("http://api.yummly.com/v1/api/recipes?_app_id=50941410&_app_key=0dc316efc98d63ab40aafb162668552d&q=" + getActivity().getResources().getStringArray(R.array.categories)[0]);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lv = (ListView) getActivity().findViewById(R.id.listView);

    }

    public class TheRecipeTask extends AsyncTask<String, Integer, String> {

        public static final String TAG = "Recipe_IntentService";

        String recipe_name;

        String image_urls, rating;
        StringBuilder stringBuilder;
        RecipeListAdapter theRLA;
        Context context;

        public TheRecipeTask(Context c) {

            context = c;

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                URL url = new URL(params[0]);

                Log.d("URL", "" + url);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.connect();

                InputStream is = connection.getInputStream();

                String jsString = IOUtils.toString(is);

                is.close();

                connection.disconnect();

                JSONObject recipeObject = new JSONObject(jsString);

                JSONArray recipeMatch = recipeObject.getJSONArray("matches");

                // Loop through JSONArray and search for what we need
                for (int i = 0; i < recipeMatch.length(); i++) {

                    JSONObject recipeOBJ = recipeMatch.getJSONObject(i);

                    String ingredients;

                    if (recipeOBJ.has("ingredients")) {

                        JSONArray ingredients_arr = recipeOBJ.getJSONArray("ingredients");

                        Log.d(TAG, "Ingredients Length " + ingredients_arr.length());


                        for (int p = 0; p < ingredients_arr.length(); p++) {

                            ingredients = "- " + ingredients_arr.getString(p);

//                        ingredientsArray.add(ingredients.concat("\n"));

                            stringBuilder = new StringBuilder();

                            stringBuilder.append(ingredients.concat("\n"));

                            Log.d(TAG, "Ingredients " + stringBuilder);

                        }

                    } else {

                        ingredients = "Ingredients N/A";

                        stringBuilder.append(ingredients.concat("\n"));

                    }


                    if (recipeOBJ.has("smallImageUrls")) {

                        JSONArray pic_arr = recipeOBJ.getJSONArray("smallImageUrls");

                        image_urls = (pic_arr.getString(0));

                    }

                    Log.d(TAG, "IMAGE " + image_urls);


                    if (recipeOBJ.has("recipeName")) {

                        recipe_name = recipeOBJ.getString("recipeName");

                    } else {

                        recipe_name = "Name N/A";

                    }

                    Log.d(TAG, "NAME " + recipe_name);


                    if (recipeOBJ.has("rating")) {

                        rating = recipeOBJ.getInt("rating") + " Stars";

                    } else {

                        rating = "Rating N/A";

                    }

                    Log.d(TAG, "Rating " + rating);


                    foodData.add(new RecipeClass(recipe_name, image_urls, stringBuilder, rating));

                }

            } catch (Exception e) {

                e.printStackTrace();

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            theRLA = new RecipeListAdapter(context, foodData);

            lv.setAdapter(theRLA);

            saveData();

        }

        protected void saveData() {

            try {

                FileInputStream fis = getActivity().openFileInput("DownloadedRecipes.dat");

                if (fis != null) {

                    FileOutputStream fos = getActivity().openFileOutput("DownloadedRecipes.dat", Context.MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(foodData);
                    oos.close();

                }
            } catch (Exception e) {

                try {

                    FileOutputStream fos = getActivity().openFileOutput("DownloadedRecipes.dat", Context.MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(foodData);
                    oos.close();


                } catch (Exception exception) {

                    exception.printStackTrace();

                }

                e.printStackTrace();

            }

        }
    }
}
