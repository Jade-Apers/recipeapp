package com.example.recipeappmobile;

import static com.example.recipeappmobile.MainActivity.EXTRA_DURATION;
import static com.example.recipeappmobile.MainActivity.EXTRA_INGREDIENTS;
import static com.example.recipeappmobile.MainActivity.EXTRA_TITLE;
import static com.example.recipeappmobile.MainActivity.EXTRA_URI;
import static com.example.recipeappmobile.MainActivity.EXTRA_URL;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    Button button;
    TextView speakText;
    TextToSpeech textToSpeech;

    //return button to home
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //translate function
        button = findViewById(R.id.translate);
        speakText = findViewById(R.id.ingredientlist);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int lang = textToSpeech.setLanguage(Locale.US);

                    if (lang == textToSpeech.LANG_MISSING_DATA || lang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(DetailActivity.this, "Language is not supported", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailActivity.this, "Language Supported", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = speakText.getText().toString();
                textToSpeech.speak(data, textToSpeech.QUEUE_FLUSH, null);
            }
        });

        //new intent
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String title = intent.getStringExtra(EXTRA_TITLE);
        int durationCount = intent.getIntExtra(EXTRA_DURATION, 0);
        String uri = intent.getStringExtra(EXTRA_URI);

        String ingredient = intent.getStringExtra(EXTRA_INGREDIENTS);
        String[] splicedIngredient = ingredient.replaceAll("\",", "\n").replaceAll("]", " ").replaceAll("\\[", "").replaceAll("\"", "\n").split(",");

        // calling the action bar
        getSupportActionBar().setTitle(R.string.back);
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        ImageView imageView = findViewById(R.id.image_view_detail);
        TextView textViewTitle = findViewById(R.id.text_view_title_detail);
        TextView textViewDuration = findViewById(R.id.text_view_duration_detail);
        Button share = findViewById(R.id.share_button);
        Button prepare = findViewById(R.id.prepare_button);

        Picasso.with(this).load(imageUrl).fit().centerInside().into(imageView);
        textViewTitle.setText(title);
        TextView textViewIngredients = findViewById(R.id.ingredientlist);

        for (int i = 0; i < splicedIngredient.length; i++) {
            textViewIngredients.append(splicedIngredient[i]);
        }

        if (durationCount == 0) {
            textViewDuration.setText(R.string.totalduration);
        } else {
            textViewDuration.setText("Time to prepare: " + durationCount + " minutes");
        }

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Ingredientlist for today");
                intent.setType("text/plain");
                startActivity(intent);
            }
        });

        prepare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, imageUrl);
                startActivity(Intent.createChooser(shareIntent, "Share button"));

                Uri uri = Uri.parse(intent.getStringExtra(EXTRA_URI)); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
}