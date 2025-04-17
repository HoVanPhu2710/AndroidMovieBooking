package com.client.androidmoviebooking.domain.usecase;

import com.client.androidmoviebooking.data.api.ApiService;
import com.client.androidmoviebooking.data.model.response.RecommendMovieResponse;
import com.client.androidmoviebooking.domain.model.RecommendMovie;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetRecommendedMovieListUseCase {
    private final ApiService apiService;

    public GetRecommendedMovieListUseCase(ApiService apiService) {
        this.apiService = apiService;
    }

    public interface OnResult {
        void onSuccess(Map<String, List<RecommendMovie>> moviesByGenre);
        void onFailure(Throwable throwable);
    }

    public void execute(OnResult onResult) {
        apiService.getRecommendedMovies().enqueue(new Callback<RecommendMovieResponse>() {
            @Override
            public void onResponse(Call<RecommendMovieResponse> call, Response<RecommendMovieResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Map<String, List<RecommendMovie>> genreMap = response.body().getData().getData();
                    onResult.onSuccess(genreMap);
                } else {
                    onResult.onFailure(new Exception("Recommended API call failed"));
                }
            }

            @Override
            public void onFailure(Call<RecommendMovieResponse> call, Throwable t) {
                onResult.onFailure(t);
            }
        });
    }
}