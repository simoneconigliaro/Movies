package com.project.simoneconigliaro.movies.util;

import com.project.simoneconigliaro.movies.models.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestUtil {

    public static final Movie TEST_MOVIE_1 = new Movie(1, "Movie #1", "2020", "sci-fi", "poster_movie1");
    public static final Movie TEST_MOVIE_2 = new Movie(2, "Movie #2", "2020", "sci-fi", "poster_movie2");


    public static final List<Movie> TEST_MOVIES_LIST = Collections.unmodifiableList(
            new ArrayList<Movie>(){{
                add(new Movie(1, "Movie #1", "2020", "sci-fi", "poster_movie1"));
                add(new Movie(2, "Movie #2", "2020", "sci-fi", "poster_movie2"));
            }}
    );
}
