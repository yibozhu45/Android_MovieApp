package com.example.project_mobile.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project_mobile.R;
import com.example.project_mobile.ui.main.module.json_handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private MovieRecViewAdapter mAdapter;

    //My variable
    private Spinner genre_spinner;
    private Spinner year_spinner;
    private Button apply_button;

    //string for genre and year
    String[] genres = {"HORROR","THRILLER","SCI-FI","CRIME","DRAMA"};
    String[]  years = {"2018","2019"};

    //selected string value
    String selectedGenre = genres[0];
    String selectedYear = years[0];

    public MovieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieFragment newInstance(String param1, String param2) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        genre_spinner = (Spinner) view.findViewById(R.id.genre_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.genre_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        genre_spinner.setAdapter(adapter);
        genre_spinner.setOnItemSelectedListener(this);

        year_spinner = view.findViewById(R.id.year_spinner);
        ArrayAdapter<CharSequence> year_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.year_array, android.R.layout.simple_spinner_item);
        year_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_spinner.setAdapter(year_adapter);
        year_spinner.setOnItemSelectedListener(this);

        //for recycle view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.movie_RecView);

        mAdapter = new MovieRecViewAdapter(getContext(), "HORROR","2018", new RecViewClickListener());

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.scrollToPosition(((LinearLayoutManager) mRecyclerView.getLayoutManager())
                .findFirstCompletelyVisibleItemPosition());

        apply_button = view.findViewById(R.id.apply_button);
        apply_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter = new MovieRecViewAdapter(getContext(), selectedGenre,selectedYear,new RecViewClickListener());

                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                mRecyclerView.scrollToPosition(((LinearLayoutManager) mRecyclerView.getLayoutManager())
                        .findFirstCompletelyVisibleItemPosition());
                //Log.d("year", selectedYear);
                //Log.d("genre", selectedGenre);
            }
        });

        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.genre_spinner)
        {
            selectedGenre = genres[i];
        }
        else if(adapterView.getId() == R.id.year_spinner)
        {
            selectedYear = years[i];
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class RecViewClickListener implements MovieRecViewAdapter.RecyclerViewClickListener {

        @Override
        public void recyclerViewListClicked(View v, int position) {
            json_handler handler = new json_handler(getContext());

            try {
                JSONObject genres = handler.getMovies().getJSONObject(selectedGenre);
                JSONArray movies_in_year = genres.getJSONArray(selectedYear);

                //position
                JSONObject movie = movies_in_year.getJSONObject(position);
                String url = movie.getString("Content");
                //Log.d("content", url);


                Intent intent = new Intent(getActivity(), Detail_Movie_Activity.class);
                intent.putExtra("URL", url);
                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

//https://stackoverflow.com/questions/28296708/get-clicked-item-and-its-position-in-recyclerview
//recycle view onclick listener