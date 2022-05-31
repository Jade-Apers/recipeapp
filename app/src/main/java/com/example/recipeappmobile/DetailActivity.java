package com.example.recipeappmobile;

import static com.example.recipeappmobile.MainActivity.EXTRA_DURATION;
import static com.example.recipeappmobile.MainActivity.EXTRA_TITLE;
import static com.example.recipeappmobile.MainActivity.EXTRA_URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String title = intent.getStringExtra(EXTRA_TITLE);
        int durationCount = intent.getIntExtra(EXTRA_DURATION, 0);

        getSupportActionBar().setTitle("Go back");

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        ImageView imageView = findViewById(R.id.image_view_detail);
        TextView textViewTitle = findViewById(R.id.text_view_title_detail);
        TextView textViewDuration = findViewById(R.id.text_view_duration_detail);
        Button share = findViewById(R.id.share_button);

        Picasso.with(this).load(imageUrl).fit().centerInside().into(imageView);
        textViewTitle.setText(title);

        if(durationCount == 0){
            textViewDuration.setText("duration: 25 minutes");
        } else {
            textViewDuration.setText("duration: " + durationCount + " minutes");
        }



    share.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, imageUrl);
            startActivity(Intent.createChooser(shareIntent, "Share button"));
        }
    });
}
}