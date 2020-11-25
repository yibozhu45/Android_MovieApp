package com.example.project_mobile.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project_mobile.R;
import com.example.project_mobile.ui.main.module.url_handler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String jsonResult = intent.getStringExtra("response");

        if (jsonResult != null) {
            try {
                JSONObject root = new JSONObject(jsonResult);
                JSONArray returnedMovies = root.getJSONArray("Search");
                int numOfMovies = returnedMovies.length();

                //scroll view
                ScrollView Layout = findViewById(R.id.layout);

                //main layout
                LinearLayout mainLayout = new LinearLayout(ResultActivity.this);
                mainLayout.setOrientation(LinearLayout.VERTICAL);

                for (int i = 0; i < numOfMovies; i++) {
                    JSONObject movie = returnedMovies.getJSONObject(i);
                    LinearLayout movieLayout = new LinearLayout(ResultActivity.this);
                    movieLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));

                    movieLayout.setOrientation(LinearLayout.VERTICAL);
                    movieLayout.setPadding(0, 50, 0,0);

                    TextView titleTextView = new TextView(ResultActivity.this);
                    titleTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));

                    titleTextView.setText(movie.getString("Title"));

                    TextView releaseYearTextView = new TextView(ResultActivity.this);
                    releaseYearTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));

                    releaseYearTextView.setText(movie.getString("Year"));

                    final TextView linkTextView = new TextView(ResultActivity.this);
                    linkTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    final String url = "https://www.imdb.com/title/"+movie.getString("imdbID");
                    linkTextView.setTextColor(Color.parseColor("#327ba8"));
                    linkTextView.setText(url);
                    linkTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ResultActivity.this, Detail_Movie_Activity.class);
                            intent.putExtra("URL", url);
                            startActivity(intent);
                        }
                    });

                    ImageView posterView = new ImageView(ResultActivity.this);
                    posterView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));

                    if (movie.has("Poster")) {
                        Picasso.get().load(movie.getString("Poster")).into(posterView);
                    }

                    movieLayout.addView(titleTextView);
                    movieLayout.addView(releaseYearTextView);
                    movieLayout.addView(linkTextView);
                    movieLayout.addView(posterView);

                    mainLayout.addView(movieLayout);
                }
                Layout.addView(mainLayout);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}