package com.client.androidmoviebooking.domain.usecase;

import android.util.Log;

import com.client.androidmoviebooking.data.api.ApiService;
import com.client.androidmoviebooking.data.model.response.MovieDetailResponse;
import com.client.androidmoviebooking.domain.model.Movie;
import com.client.androidmoviebooking.domain.model.MovieDetail;

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
        void onSuccess(MovieDetail movieDetail);
        void onFailure(Throwable throwable);
    }

    public void execute(String movieSlug, OnResult onResult) {
        Log.d("GetMovieDetailUseCase", "Starting API call for movieSlug: " + movieSlug);
        apiService.getMovieDetail(movieSlug).enqueue(new Callback<MovieDetailResponse>() {
            @Override
            public void onResponse(Call<MovieDetailResponse> call, Response<MovieDetailResponse> response) {
                Log.d("GetMovieDetailUseCase", "Received response with code: " + response.code());
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Log.d("GetMovieDetailUseCase", "API call successful, data received");
                    MovieDetail movieDetail = response.body().getData();
                    if (movieDetail != null) {
                        Log.d("GetMovieDetailUseCase", "MovieDetail retrieved: " + movieDetail.getTitle());
                        onResult.onSuccess(movieDetail);
                    } else {
                        Log.e("GetMovieDetailUseCase", "MovieDetail is null");
                        onResult.onFailure(new Exception("Movie detail data is null"));
                    }
                } else {
                    String errorMsg = "API call failed: " +
                            "isSuccessful=" + response.isSuccessful() +
                            ", body=" + (response.body() != null ? response.body().toString() : "null") +
                            ", responseCode=" + response.code();
                    Log.e("GetMovieDetailUseCase", errorMsg);
                    onResult.onFailure(new Exception("Movie detail API call failed: " + errorMsg));
                }
            }

            @Override
            public void onFailure(Call<MovieDetailResponse> call, Throwable t) {
                Log.e("GetMovieDetailUseCase", "API call failed: " + t.getMessage(), t);
                onResult.onFailure(t);
            }
        });
    }
}