package com.project.simoneconigliaro.movies;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.project.simoneconigliaro.movies.persistence.MovieDao;
import com.project.simoneconigliaro.movies.persistence.MovieDatabase;

import org.junit.After;
import org.junit.Before;

public abstract class MovieDatabaseTest {

    // system under test
    private MovieDatabase movieDatabase;

    public MovieDao getMovieDao(){
        return movieDatabase.getMovieDao();
    }

    @Before
    public void init(){
        movieDatabase = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                MovieDatabase.class
        ).build();
    }

    @After
    public void finish(){
        movieDatabase.close();
    }


}
