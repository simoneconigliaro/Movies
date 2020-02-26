package com.project.simoneconigliaro.movies.network;

import com.project.simoneconigliaro.movies.models.Movie;
import com.project.simoneconigliaro.movies.util.Constants;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MovieApiTest {

    private MovieApi movieApi;

    @BeforeEach
    public void createService() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.readTimeout(Constants.READ_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.MILLISECONDS);

        movieApi = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build()
                .create(MovieApi.class);
    }

    @Test
    public void getListMovies(){
        try {
            Response response = movieApi.getListMovies().execute();
            assertEquals(response.code(), 200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
