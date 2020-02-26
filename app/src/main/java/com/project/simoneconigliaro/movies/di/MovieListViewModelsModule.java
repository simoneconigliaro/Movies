package com.project.simoneconigliaro.movies.di;

import androidx.lifecycle.ViewModel;

import com.project.simoneconigliaro.movies.di.ViewModelKey;
import com.project.simoneconigliaro.movies.viewmodels.MovieListViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MovieListViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel.class)
    public abstract ViewModel bindMovieListViewModel(MovieListViewModel viewModel);

}
