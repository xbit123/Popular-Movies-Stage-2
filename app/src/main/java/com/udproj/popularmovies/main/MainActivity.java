package com.udproj.popularmovies.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.udproj.popularmovies.details.DetailsActivity;
import com.udproj.popularmovies.R;
import com.udproj.popularmovies.model.Movie;
import com.udproj.popularmovies.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainMoviesAdapter.MoviesAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.rv_main) RecyclerView mRvMain;
    @BindView(R.id.tv_error) TextView mTvError;
    @BindView(R.id.pb_load) ProgressBar mLoadingIndicator;

    private MainMoviesAdapter mMainMoviesAdapter;
    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupRv();

        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        updateTitle();
        attachMoviesLiveData();
    }

    private void updateTitle() {
        String currentList = mMainViewModel.getCurrentList();
        String title = "";
        switch (currentList) {
            case Constants.MOST_POPULAR:
                title = getResources().getString(R.string.most_popular) + " " + getResources().getString(R.string.title_p2);
                break;
            case Constants.TOP_RATED:
                title = getResources().getString(R.string.top_rated) + " " + getResources().getString(R.string.title_p2);
                break;
            case Constants.FAVORITE:
                title = getResources().getString(R.string.favorite) + " " + getResources().getString(R.string.title_p2);
                break;
        }
        setTitle(title);
    }

    private void setupRv() {
        int cols = getResources().getInteger(R.integer.movies_list_columns);
        GridLayoutManager layoutManager = new GridLayoutManager(this, cols);
        mRvMain.setLayoutManager(layoutManager);
        mRvMain.setHasFixedSize(true);

        mMainMoviesAdapter = new MainMoviesAdapter(this);
        mRvMain.setAdapter(mMainMoviesAdapter);
    }

    private void attachMoviesLiveData() {
        mMainViewModel.getmMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                mMainMoviesAdapter.setMoviesData(movies);
            }
        });
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

        switch (id) {
            case R.id.menu_most_popular:
                mMainViewModel.setCurrentList(Constants.MOST_POPULAR);
                break;
            case R.id.menu_top_rated:
                mMainViewModel.setCurrentList(Constants.TOP_RATED);
                break;
            case R.id.menu_favorite:
                mMainViewModel.setCurrentList(Constants.FAVORITE);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        attachMoviesLiveData();
        updateTitle();
        return true;
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("Movie", movie);
        intent.putExtra(Constants.FAVORITE, mMainViewModel.isFavorite(movie));
        startActivity(intent);
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
