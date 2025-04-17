package com.client.androidmoviebooking.domain.usecase;

import com.client.androidmoviebooking.data.api.ApiService;
import com.client.androidmoviebooking.data.model.response.MovieResponse;
import com.client.androidmoviebooking.domain.model.Movie;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetMovieListUseCase {
    private final ApiService apiService;

    public GetMovieListUseCase(ApiService apiService) {
        this.apiService = apiService;
    }

    public interface OnResult {
        void onSuccess(List<Movie> popularMovies);
        void onFailure(Throwable throwable);
    }

    public void execute(OnResult onResult) {
        apiService.getMovies().enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<Movie> popularMovies = response.body().getData().getData();
                    onResult.onSuccess(popularMovies != null ? popularMovies : new ArrayList<>());
                } else {
                    onResult.onFailure(new Exception("Movies API call failed"));
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                onResult.onFailure(t);
            }
        });
    }
}