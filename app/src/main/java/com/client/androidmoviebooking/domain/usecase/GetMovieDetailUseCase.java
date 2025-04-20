package com.client.androidmoviebooking.domain.usecase;

import com.client.androidmoviebooking.data.api.ApiService;
import com.client.androidmoviebooking.data.model.response.MovieDetailResponse;
import com.client.androidmoviebooking.domain.model.Movie;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetMovieDetailUseCase {
    private final ApiService apiService;

    @Inject
    public GetMovieDetailUseCase(ApiService apiService) {
        this.apiService = apiService;
    }

    public interface OnResult {
        void onSuccess(Movie movie);
        void onFailure(Throwable throwable);
    }

    public void execute(int movieId, OnResult onResult) {
        apiService.getMovieDetail(movieId).enqueue(new Callback<MovieDetailResponse>() {
            @Override
            public void onResponse(Call<MovieDetailResponse> call, Response<MovieDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Movie movie = response.body().getData();
                    onResult.onSuccess(movie);
                } else {
                    onResult.onFailure(new Exception("Movie detail API call failed"));
                }
            }

            @Override
            public void onFailure(Call<MovieDetailResponse> call, Throwable t) {
                onResult.onFailure(t);
            }
        });
    }
}