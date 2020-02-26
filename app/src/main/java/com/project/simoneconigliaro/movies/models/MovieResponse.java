package com.project.simoneconigliaro.movies.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

    @SerializedName("data")
    @Expose
    private List<Movie> listMovies;

    public List<Movie> getListMovies() {
        return listMovies;
    }

}
