package com.project.simoneconigliaro.movies.di;

import androidx.lifecycle.ViewModelProvider;

import com.project.simoneconigliaro.movies.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory modelProviderFactory);
}
