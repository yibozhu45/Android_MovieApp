package com.example.project_mobile.ui.main.module;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class json_handler {

    private JSONObject movies = null;

    public json_handler(Context context) {
        try {
            InputStream is = context.getAssets().open("movies.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            JSONObject root = new JSONObject(json);
            movies = root.getJSONObject("movies");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getMovies() {
        return movies;
    }
}
