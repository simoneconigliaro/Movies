package com.project.simoneconigliaro.movies.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.project.simoneconigliaro.movies.models.Movie;
import com.project.simoneconigliaro.movies.models.MovieResponse;
import com.project.simoneconigliaro.movies.network.MovieApi;
import com.project.simoneconigliaro.movies.persistence.MovieDao;
import com.project.simoneconigliaro.movies.util.NetworkBoundResource;
import com.project.simoneconigliaro.movies.util.Resource;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

import static com.project.simoneconigliaro.movies.util.Constants.REFRESH_TIME;


public class MovieRepository {

    private static final String TAG = "MovieRepository";

    private MovieApi movieApi;
    private MovieDao movieDao;

    @Inject
    public MovieRepository(MovieApi movieApi, MovieDao movieDao) {
        this.movieApi = movieApi;
        this.movieDao = movieDao;
    }

    public LiveData<Resource<List<Movie>>> getListMovies() {
        return new NetworkBoundResource<List<Movie>, MovieResponse>() {


            @Override
            protected void saveCallResult(MovieResponse item) {

                if (null != item) {

                    int currentTime = (int) (System.currentTimeMillis() / 1000);

                    for (int i = 0; i <= (item.getListMovies().size() - 1); i++) {
                        item.getListMovies().get(i).setTimestamp(currentTime);
                    }

                    if (movieDao.getMovies() != null) {
                        movieDao.deleteMovies();
                    }

                    movieDao.saveMovies(item.getListMovies());
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Movie> data) {

                if (data == null || data.size() == 0) {
                    return true;
                } else {

                    int currentTime = (int) (System.currentTimeMillis() / 1000);
                    Log.d(TAG, "shouldFetch: current time " + currentTime);
                    int lastRefresh = data.get(0).getTimestamp();
                    Log.d(TAG, "shouldFetch: last refresh " + lastRefresh);

                    Log.d(TAG, "shouldFetch: it's been " + (currentTime - lastRefresh) / 60 + " minutes since last refresh" +
                            " the cache will be refreshed after 10 minutes");
                    if (currentTime - lastRefresh >= REFRESH_TIME) {
                        Log.d(TAG, "shouldFetch: cache will be refreshed");
                        return true;
                    } else {
                        return false;
                    }
                }
            }

            @NonNull
            @Override
            protected LiveData<List<Movie>> loadFromDb() {
                return movieDao.getMovies();
            }

            @NonNull
            @Override
            protected Call<MovieResponse> createCall() {
                return movieApi.getListMovies();
            }
        }.getAsLiveData();
    }
}