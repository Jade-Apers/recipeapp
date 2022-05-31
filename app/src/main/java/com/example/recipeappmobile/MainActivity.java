package com.example.recipeappmobile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mExampleList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        downloadAllRecipes();
    }

    private void downloadAllRecipes(){
        String url = "https://api.edamam.com/api/recipes/v2?type=public&q=pasta&app_id=a19cf056&app_key=%20792806ee4d0f0dfc32e09e98502e34ec%09";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        try {
                            JSONArray jsonArray = response.getJSONArray("hits");

                            for(int i = 0 ; i <jsonArray.length() ; i++){
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String title = hit.getString("label");
                                String imageUrl = hit.getString("image");
                                int duration = hit.getInt("totalTime");

                                mExampleList.add(new ExampleItem(imageUrl, title, duration));
                            }
                            mExampleAdapter = new ExampleAdapter(MainActivity.this , mExampleList);
                            mRecyclerView.setAdapter(mExampleAdapter);

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        });

        mRequestQueue.add(request);

    }
}