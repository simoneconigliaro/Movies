package com.project.simoneconigliaro.movies.network;

import com.project.simoneconigliaro.movies.models.MovieResponse;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApi {

    @GET("api/movies")
    Call<MovieResponse> getListMovies();
}
