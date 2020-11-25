package com.example.project_mobile.ui.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_mobile.R;
import com.example.project_mobile.ui.main.module.json_handler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MovieRecViewAdapter extends RecyclerView.Adapter<MovieRecViewAdapter.ViewHolder>  {

    private Context context;
    private static RecyclerViewClickListener itemListener;
    private JSONObject movies = null;
    private JSONObject genres = null;
    //separate variables
    private String genre;
    private String year;

    public MovieRecViewAdapter(Context context, String genre, String year) {
        json_handler handler = new json_handler(context);
        movies = handler.getMovies();
        this.genre = genre;
        this.year = year;
        this.context = context;
    }

    public interface RecyclerViewClickListener {
        public void recyclerViewListClicked(View v, int position);
    }

    public MovieRecViewAdapter(Context context, String genre, String year, RecyclerViewClickListener itemListener) {
        json_handler handler = new json_handler(context);
        movies = handler.getMovies();
        this.genre = genre;
        this.year = year;
        this.context = context;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_cell, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            genres = movies.getJSONObject(genre);
            JSONArray movies_in_year = genres.getJSONArray(year);
            JSONObject movie = movies_in_year.getJSONObject(position);
            holder.getName().setText(movie.getString("Title"));
            holder.getYear().setText(movie.getString("Year"));


            Glide.with(context)
                    .asBitmap()
                    .load(movie.getString("Poster"))
                    .into(holder.getPoster());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        try {
            genres = movies.getJSONObject(genre);
            JSONArray movies_in_year = genres.getJSONArray(year);
            return movies_in_year.length();
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView name;
        private TextView year;
        private ImageView poster;
        private CardView parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txtName);
            year = (TextView) itemView.findViewById(R.id.txtYear);
            poster = (ImageView) itemView.findViewById(R.id.image);
            parent = itemView.findViewById(R.id.parent);
            itemView.setOnClickListener(this);
        }

        public TextView getName() {
            return name;
        }

        public TextView getYear() {
            return year;
        }

        public ImageView getPoster() {
            return poster;
        }

        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
        }
    }
}

/*
            InputStream ims = null;
            try {
                ims = context.getAssets().open("halloween.jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Drawable d = Drawable.createFromStream(ims, null);
            holder.getPoster().setImageDrawable(d);

            Glide.with(context)
                    .asBitmap()
                    .load(movie.getString("Poster"))
                    .into(holder.getPoster());

            Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(holder.getPoster());

            Picasso.get()
                    .load("http://i.imgur.com/DvpvklR.png")
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.getPoster());
*/