package com.project.simoneconigliaro.movies.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.project.simoneconigliaro.movies.models.Movie;

import java.util.List;


@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveMovies(List<Movie> listMovies);

    @Query("DELETE FROM movies")
    void deleteMovies();





}
