package com.project.simoneconigliaro.movies.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MovieTest {

    /*
        Compare two equal Movies
     */

    @Test
    void isMovieEqual_identicalProperties_returnTrue() throws Exception {

        // Arrange
        Movie movie1 = new Movie(1, "Movie #1", "2020", "sci-fi", "poster_movie1");
        Movie movie2 = new Movie(1, "Movie #1", "2020", "sci-fi", "poster_movie1");

        // Act


        // Assert
        assertEquals(movie1, movie2);
        System.out.println("The movies are equal!");
    }

    /*
        Compare movies with 2 different ids
     */

    @Test
    void isMoviesEqual_differentIds_returnFalse() throws Exception {
        // Arrange
        Movie movie1 = new Movie(1, "Movie #1", "2020", "sci-fi", "poster_movie1");
        Movie movie2 = new Movie(2, "Movie #1", "2020", "sci-fi", "poster_movie1");

        // Act

        // Assert
        assertNotEquals(movie1, movie2);
        System.out.println("The movies are not equal!");
    }

    /*
        Compare two notes with different titles
     */

    @Test
    void isMoviesEqual_differentTitles_returnFalse() throws Exception {
        // Arrange
        Movie movie1 = new Movie(1, "Movie #1", "2020", "sci-fi", "poster_movie1");
        Movie movie2 = new Movie(1, "Movie #2", "2020", "sci-fi", "poster_movie1");


        // Act

        // Assert
        assertNotEquals(movie1, movie2);
        System.out.println("The movies are not equal!");
    }

}
