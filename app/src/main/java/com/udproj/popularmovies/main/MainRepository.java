package com.udproj.popularmovies.main;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.udproj.popularmovies.database.MovieDatabase;
import com.udproj.popularmovies.database.MovieDao;
import com.udproj.popularmovies.model.Movie;
import com.udproj.popularmovies.utils.AppExecutors;
import com.udproj.popularmovies.utils.Constants;
import com.udproj.popularmovies.utils.JsonUtils;
import com.udproj.popularmovies.utils.NetworkUtils;

import java.util.List;

public class MainRepository {
    private MovieDao mMovieDao;
    private MutableLiveData<List<Movie>> mPopularMovies;
    private MutableLiveData<List<Movie>> mTopRatedMovies;

    public MainRepository(Application application, MutableLiveData<List<Movie>> popular, MutableLiveData<List<Movie>> topRated) {
        MovieDatabase db = MovieDatabase.getInstance(application);
        mMovieDao = db.movieDao();
        this.mPopularMovies = popular;
        this.mTopRatedMovies = topRated;
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return mMovieDao.loadAllFavorite();
    }

    public void requestTopOrPopularMovies(final String currentList) {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String jsonMoviesResponse = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildMovieListUrl(currentList));
                    List<Movie> movies = JsonUtils.getMoviesFromJson(jsonMoviesResponse);
                    if (currentList.equals(Constants.MOST_POPULAR)) {
                        mPopularMovies.postValue(movies);
                    } else {
                        mTopRatedMovies.postValue(movies);
                    }
                } catch (Exception e) {

                }
            }
        });
    }
}
