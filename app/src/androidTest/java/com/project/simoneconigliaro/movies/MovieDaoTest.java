package com.project.simoneconigliaro.movies;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.project.simoneconigliaro.movies.models.Movie;
import com.project.simoneconigliaro.movies.util.LiveDataTestUtil;
import com.project.simoneconigliaro.movies.util.TestUtil;

import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MovieDaoTest extends MovieDatabaseTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();



    @Test
    public void insertReadDelete() throws Exception {

        List<Movie> listMovies = TestUtil.TEST_MOVIES_LIST;

        // insert
        getMovieDao().saveMovies(listMovies);

        // read
        LiveDataTestUtil<List<Movie>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<Movie> insertedMovies = liveDataTestUtil.getValue(getMovieDao().getMovies());

        assertNotNull(insertedMovies);

        assertEquals(listMovies.get(0).getId(), insertedMovies.get(0).getId());
        assertEquals(listMovies.get(1).getId(), insertedMovies.get(1).getId());


        // delete
        getMovieDao().deleteMovies();

        // confirm the database is empty
        insertedMovies = liveDataTestUtil.getValue(getMovieDao().getMovies());
        assertEquals(0, insertedMovies.size());

    }


}
