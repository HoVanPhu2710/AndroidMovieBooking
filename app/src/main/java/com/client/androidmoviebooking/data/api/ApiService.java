package com.client.androidmoviebooking.data.api;

import com.client.androidmoviebooking.data.model.response.MovieResponse;
import com.client.androidmoviebooking.data.model.response.RecommendMovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("movies")
    Call<MovieResponse> getMovies();
    @GET("movies/recommended")
    Call<RecommendMovieResponse> getRecommendedMovies();
}