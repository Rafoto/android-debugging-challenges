package com.codepath.debuggingchallenges.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.codepath.debuggingchallenges.R;
import com.codepath.debuggingchallenges.adapters.MoviesAdapter;
import com.codepath.debuggingchallenges.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MoviesActivity extends AppCompatActivity {

    private static final String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public final static String API_KEY_PARAM = "api_key";
    RecyclerView rvMovies;
    MoviesAdapter adapter;
    ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fetchMovies();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        rvMovies = findViewById(R.id.rvMovies);
        // Create the adapter to convert the array to views
        adapter = new MoviesAdapter(movies);

        // Attach the adapter to a ListView
        rvMovies.setAdapter(adapter);

    }


    private void fetchMovies() {
        String url = " https://api.themoviedb.org/3/movie/now_playing?api_key=";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.api_key));
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray moviesJson = response.getJSONArray("results");
                    movies = Movie.fromJSONArray(moviesJson);
                } catch (JSONException e) {
                    Toast.makeText(MoviesActivity.this, "Failed to Get movie", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }
}
