package com.project.simoneconigliaro.movies.di;

import android.app.Application;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.project.simoneconigliaro.movies.R;
import com.project.simoneconigliaro.movies.network.MovieApi;
import com.project.simoneconigliaro.movies.persistence.MovieDao;
import com.project.simoneconigliaro.movies.persistence.MovieDatabase;
import com.project.simoneconigliaro.movies.repository.MovieRepository;
import com.project.simoneconigliaro.movies.ui.MovieAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.project.simoneconigliaro.movies.persistence.MovieDatabase.DATABASE_NAME;
import static com.project.simoneconigliaro.movies.util.Constants.BASE_URL;

@Module
public class AppModule {

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    static RequestOptions provideRequestOptions(){
        return RequestOptions
                .placeholderOf(R.drawable.placeholder)
                .error(R.drawable.placeholder_error);
    }

    @Singleton
    @Provides
    static RequestManager provideGlideInstance(Application application, RequestOptions requestOptions){
        return Glide.with(application)
                .setDefaultRequestOptions(requestOptions);
    }

    @Provides
    static MovieAdapter provideMovieAdapter(RequestManager requestManager) {
        return new MovieAdapter(requestManager);
    }

    @Provides
    static MovieApi provideMovieApi(Retrofit retrofit) {
        return retrofit.create(MovieApi.class);
    }


    @Singleton
    @Provides
    static MovieRepository provideMovieRepository(MovieApi movieApi, MovieDao movieDao){
        return new MovieRepository(movieApi, movieDao);
    }

    @Singleton
    @Provides
    static MovieDatabase provideMovieDatabase(Application application){
        return Room.databaseBuilder(
                application,
                MovieDatabase.class,
                DATABASE_NAME

        ).build();
    }

    @Singleton
    @Provides
    static MovieDao provideMovieDao(MovieDatabase movieDatabase){
        return movieDatabase.getMovieDao();
    }



}
