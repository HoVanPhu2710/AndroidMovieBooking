package com.client.androidmoviebooking.data.api;

import com.client.androidmoviebooking.data.model.response.CityResponse;
import com.client.androidmoviebooking.data.model.response.DateTimeResponse;
import com.client.androidmoviebooking.data.model.response.MovieDetailResponse;
import com.client.androidmoviebooking.data.model.response.MovieResponse;
import com.client.androidmoviebooking.data.model.response.RecommendMovieResponse;
import com.client.androidmoviebooking.data.model.response.TheaterBrandResponse;
import com.client.androidmoviebooking.data.model.response.TheaterMoviesResponse;
import com.client.androidmoviebooking.data.model.response.TheaterResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movies")
    Call<MovieResponse> getMovies();

    @GET("movies/recommended")
    Call<RecommendMovieResponse> getRecommendedMovies();

    @GET("movies/{slug}")
    Call<MovieDetailResponse> getMovieDetail(@Path("slug") String slug);

    @GET("theater-brands")
    Call<TheaterBrandResponse> getTheaterBrands(@Query("city") String city);

    @GET("theaters")
    Call<TheaterResponse> getTheaters(@Query("brandId") int brandId, @Query("city") String city);

    @GET("theaters/cities")
    Call<CityResponse> getCities();

    @GET("theaters/recommend")
    Call<TheaterResponse> getTheaterRecommends(@Query("city") String city);

    @GET("theaters/list_day")
    Call<DateTimeResponse> getTheaterDays(@Query("days") int days);

    @GET("theaters/{theaterId}/movies")
    Call<TheaterMoviesResponse> getTheaterMovies(@Path("theaterId") int theaterId, @Query("date") String date);
}