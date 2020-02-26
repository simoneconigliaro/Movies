package com.project.simoneconigliaro.movies.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.project.simoneconigliaro.movies.models.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "movies_db";

    public abstract MovieDao getMovieDao();
}

