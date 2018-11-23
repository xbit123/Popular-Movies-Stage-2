package com.udproj.popularmovies.details;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.udproj.popularmovies.database.MovieDao;
import com.udproj.popularmovies.database.MovieDatabase;
import com.udproj.popularmovies.model.Movie;
import com.udproj.popularmovies.model.Review;
import com.udproj.popularmovies.model.Trailer;
import com.udproj.popularmovies.utils.AppExecutors;
import com.udproj.popularmovies.utils.JsonUtils;
import com.udproj.popularmovies.utils.NetworkUtils;

import java.net.URL;
import java.util.List;

public class DetailsRepository {
    private MovieDao mMovieDao;
    private MutableLiveData<List<Review>> mReviews;
    private MutableLiveData<List<Trailer>> mTrailers;

    public DetailsRepository(Application application, MutableLiveData<List<Review>> reviews, MutableLiveData<List<Trailer>> trailers) {
        MovieDatabase db = MovieDatabase.getInstance(application);
        mMovieDao = db.movieDao();
        this.mReviews = reviews;
        this.mTrailers = trailers;
    }

    public LiveData<Movie> requestMovieById(int movieId) {
        return mMovieDao.loadFavoriteById(movieId);
    }

    public void requestReviews(final int movieId) {
        final URL url = NetworkUtils.buildReviewsUrl(movieId);
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String jsonReviewsResponse = NetworkUtils.getResponseFromHttpUrl(url);
                    List<Review> r = JsonUtils.getReviewsFromJson(jsonReviewsResponse);
                    mReviews.postValue(r);
                } catch (Exception e) {
                    //No internet or can't read JSON response
                    e.printStackTrace();
                }
            }
        });
    }

    public void requestTrailers(final int movieId) {
        final URL url = NetworkUtils.buildTrailersUrl(movieId);
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String jsonTrailersResponse = NetworkUtils.getResponseFromHttpUrl(url);
                    List<Trailer> t = JsonUtils.getTrailersFromJson(jsonTrailersResponse);
                    mTrailers.postValue(t);
                } catch (Exception e) {
                    //No internet or can't read JSON response
                    e.printStackTrace();
                }
            }
        });
    }

    public void deleteFavorite(final Movie movie) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMovieDao.deleteFavorite(movie);
            }
        });
    }

    public void insertFavorite(final Movie movie) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMovieDao.insertFavorite(movie);
            }
        });
    }
}
