package com.udproj.popularmovies.details;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.udproj.popularmovies.model.Movie;

public class DetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Application app;
    private final Movie mMovie;

    public DetailsViewModelFactory(Application app, Movie movie) {
        this.app = app;
        this.mMovie = movie;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new DetailsViewModel(app, mMovie);
    }
}