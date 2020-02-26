package com.project.simoneconigliaro.movies.di;

import com.project.simoneconigliaro.movies.ui.MovieListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {MovieListViewModelsModule.class})
    abstract MovieListActivity contributeMovieListActivity();

}
