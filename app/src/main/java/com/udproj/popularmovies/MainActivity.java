package com.udproj.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterOnClickHandler {

    private static final int COL_COUNT = 2;
    private static final String MOST_POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";

    private RecyclerView mRvMain;
    private TextView mTvError;
    private ProgressBar mLoadingIndicator;
    private MoviesAdapter mMoviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRvMain = findViewById(R.id.rv_main);
        mTvError = findViewById(R.id.tv_error);
        mLoadingIndicator = findViewById(R.id.pb_load);

        GridLayoutManager layoutManager = new GridLayoutManager(this, COL_COUNT);
        mRvMain.setLayoutManager(layoutManager);
        mRvMain.setHasFixedSize(true);

        mMoviesAdapter = new MoviesAdapter(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mMoviesAdapter.setImageWidth(displayMetrics.widthPixels / COL_COUNT);
        mRvMain.setAdapter(mMoviesAdapter);

        loadMoviesData(MOST_POPULAR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_most_popular) {
            loadMoviesData(MOST_POPULAR);
            return true;
        }

        if (id == R.id.menu_top_rated) {
            loadMoviesData(TOP_RATED);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("Movie", movie);
        startActivity(intent);
    }

    private void loadMoviesData(String list) {
        showMovieDataView();
        URL url = NetworkUtils.buildMovieListUrl(list);
        new FetchMoviesTask().execute(url);
    }

    //AsyncTask to receive Movie array from passed URL
    public class FetchMoviesTask extends AsyncTask<URL, Void, Movie[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie[] doInBackground(URL... params) {
            if (params.length == 0) {
                return null;
            }

            URL url = params[0];
            try {
                String jsonMoviesResponse = NetworkUtils.getResponseFromHttpUrl(url);
                return JsonUtils.getMoviesFromJson(jsonMoviesResponse);
            } catch (Exception e) {
                //No internet or can't read JSON response
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies != null) {
                mMoviesAdapter.setMoviesData(movies);
                showMovieDataView();
            } else {
                //No internet or can't read JSON response
                showError();
            }
        }
    }

    //Shows data on successful load
    private void showMovieDataView() {
        mTvError.setVisibility(View.INVISIBLE);
        mRvMain.setVisibility(View.VISIBLE);
    }

    //Hides data on error
    private void showError() {
        mTvError.setVisibility(View.VISIBLE);
        mRvMain.setVisibility(View.INVISIBLE);
    }
}
