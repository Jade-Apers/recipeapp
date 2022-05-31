package com.example.recipeappmobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExampleAdapter.OnItemClickListener {
    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_DURATION = "duration";

    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;

    private EditText mEdit;
    private static String DEFAULT_QUERY = "meat";
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView = findViewById(R.id.searchView);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mExampleList = new ArrayList<>();
        mExampleAdapter = new ExampleAdapter(MainActivity.this, mExampleList);
        mRecyclerView.setAdapter(mExampleAdapter);

        mRequestQueue = Volley.newRequestQueue(this);
        downloadAllRecipes(DEFAULT_QUERY);

        getSupportActionBar().setTitle("Choose your recipe \uD83C\uDF7D");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {)

            @Override
            public boolean onQueryTextSubmit(String query) {
                downloadAllRecipes(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });



    }


    private void downloadAllRecipes(String query) {
        String url = "https://api.edamam.com/api/recipes/v2?type=public&q=" + query + "&app_id=a19cf056&app_key=%20792806ee4d0f0dfc32e09e98502e34ec%09";
        mExampleList.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject recipe = jsonArray.getJSONObject(i);

                        String title = recipe.getJSONObject("recipe").getString("label");
                        String imageUrl = recipe.getJSONObject("recipe").getString("image");
                        int duration = recipe.getJSONObject("recipe").getInt("totalTime");
                        String ingredients = recipe.getJSONObject("recipe").getString("ingredientLines");
                        String type = recipe.getJSONObject("recipe").getString("mealType");

                        mExampleList.add(new ExampleItem(imageUrl, title, duration));
                    }
                    mExampleAdapter = new ExampleAdapter(MainActivity.this, mExampleList);
                    mRecyclerView.setAdapter(mExampleAdapter);
                    mExampleAdapter.setOnItemClickListener(MainActivity.this);

                    mExampleAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }



    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        ExampleItem clickedItem = mExampleList.get(position);
        detailIntent.putExtra(EXTRA_URL, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_TITLE, clickedItem.getTitle());
        detailIntent.putExtra(EXTRA_DURATION, clickedItem.getDuration());

        startActivity(detailIntent);
    }
}
