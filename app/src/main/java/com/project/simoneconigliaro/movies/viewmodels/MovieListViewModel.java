package com.project.simoneconigliaro.movies.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.project.simoneconigliaro.movies.models.Movie;
import com.project.simoneconigliaro.movies.repository.MovieRepository;
import com.project.simoneconigliaro.movies.util.Resource;


import java.util.List;

import javax.inject.Inject;


public class MovieListViewModel extends ViewModel {
    private static final String TAG = "MovieListViewModel";


    private LiveData<Resource<List<Movie>>> listMovies;

    @Inject
    public MovieListViewModel(MovieRepository movieRepository) {
        listMovies = movieRepository.getListMovies();
    }

    public LiveData<Resource<List<Movie>>> getListMovies() {
        return listMovies;
    }

}
