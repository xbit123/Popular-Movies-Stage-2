package com.udproj.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.udproj.popularmovies.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movie ORDER BY id")
    LiveData<List<Movie>> loadAllFavorite();

    @Query("SELECT * FROM movie WHERE id = :id")
    LiveData<Movie> loadFavoriteById(int id);

    @Insert
    void insertFavorite(Movie movie);

    @Delete
    void deleteFavorite(Movie movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavorite(Movie movie);
}
