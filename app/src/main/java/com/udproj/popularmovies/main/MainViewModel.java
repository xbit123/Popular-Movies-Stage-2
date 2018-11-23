package com.udproj.popularmovies.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.udproj.popularmovies.model.Movie;
import com.udproj.popularmovies.utils.Constants;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private MediatorLiveData<List<Movie>> mMovies = new MediatorLiveData<>();
    private LiveData<List<Movie>> mFavoriteMovies;
    private MutableLiveData<List<Movie>> mPopularMovies = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> mTopRatedMovies = new MutableLiveData<>();
    private MainRepository mainRepository;
    private String currentList;

    public MainViewModel(Application application) {
        super(application);
        mainRepository = new MainRepository(application, mPopularMovies, mTopRatedMovies);
        mFavoriteMovies = mainRepository.getFavoriteMovies();

        mFavoriteMovies.observeForever(new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {

            }
        });
        mainRepository.requestTopOrPopularMovies(Constants.MOST_POPULAR);
        mainRepository.requestTopOrPopularMovies(Constants.TOP_RATED);

        setCurrentList(Constants.MOST_POPULAR);
    }

    public MediatorLiveData<List<Movie>> getmMovies() {
        return mMovies;
    }

    public void setCurrentList(String currentList) {
        mMovies.removeSource(mFavoriteMovies);
        mMovies.removeSource(mPopularMovies);
        mMovies.removeSource(mTopRatedMovies);
        switch (currentList) {
            case Constants.FAVORITE:
                mMovies.addSource(mFavoriteMovies, new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> movies) {
                        mMovies.setValue(movies);
                    }
                });
                break;
            case Constants.MOST_POPULAR:
                mMovies.addSource(mPopularMovies, new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> movies) {
                        mMovies.setValue(movies);
                    }
                });
                break;
            case Constants.TOP_RATED:
                mMovies.addSource(mTopRatedMovies, new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> movies) {
                        mMovies.setValue(movies);
                    }
                });
                break;
        }
        this.currentList = currentList;
    }

    public String getCurrentList() {
        return currentList;
    }

    public Boolean isFavorite(Movie movie) {
        List<Movie> favorites = mFavoriteMovies.getValue();
        for (int i = 0; i < favorites.size(); i++) {
            if (movie.getId() == favorites.get(i).getId()) {
                return true;
            }
        }
        return false;
    }
}
