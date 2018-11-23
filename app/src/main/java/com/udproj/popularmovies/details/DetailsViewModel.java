package com.udproj.popularmovies.details;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.udproj.popularmovies.model.Movie;
import com.udproj.popularmovies.model.Review;
import com.udproj.popularmovies.model.Trailer;

import java.util.List;

public class DetailsViewModel extends ViewModel {
    private DetailsRepository detailsRepository;

    private Movie mMovie;
    private MutableLiveData<List<Review>> mReviews = new MutableLiveData<>();
    private MutableLiveData<List<Trailer>> mTrailers = new MutableLiveData<>();

    public DetailsViewModel(Application application, Movie movie) {
        detailsRepository = new DetailsRepository(application, mReviews, mTrailers);
        this.mMovie = movie;
    }

    public LiveData<List<Review>> getReviews() {
        detailsRepository.requestReviews(mMovie.getId());
        return mReviews;
    }

    public LiveData<List<Trailer>> getTrailers() {
        detailsRepository.requestTrailers(mMovie.getId());
        return mTrailers;
    }

    public void markUnfavorite() {
        detailsRepository.deleteFavorite(mMovie);
    }

    public void markFavorite() {
        detailsRepository.insertFavorite(mMovie);
    }
}
