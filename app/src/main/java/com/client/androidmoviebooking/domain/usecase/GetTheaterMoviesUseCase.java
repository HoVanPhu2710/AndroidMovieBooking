package com.client.androidmoviebooking.domain.usecase;

import com.client.androidmoviebooking.data.api.ApiService;
import com.client.androidmoviebooking.data.model.response.TheaterMoviesResponse;
import com.client.androidmoviebooking.domain.model.theater.Showtime;
import com.client.androidmoviebooking.domain.model.theater.Theater;
import com.client.androidmoviebooking.domain.model.theater.TheaterMovie;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetTheaterMoviesUseCase {
    private final ApiService apiService;

    public GetTheaterMoviesUseCase(ApiService apiService) {
        this.apiService = apiService;
    }

    public interface OnResult {
        void onSuccess(Theater theater, List<TheaterMovie> movies);
        void onFailure(Throwable throwable);
    }

    public void execute(int theaterId, String date, OnResult onResult) {
        if (theaterId <= 0 || date == null || date.trim().isEmpty()) {
            onResult.onFailure(new IllegalArgumentException("Invalid theater ID or date"));
            return;
        }

        apiService.getTheaterMovies(theaterId, date).enqueue(new Callback<TheaterMoviesResponse>() {
            @Override
            public void onResponse(Call<TheaterMoviesResponse> call, Response<TheaterMoviesResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<TheaterMovie> movies = response.body().getData();
                    Theater theater = extractTheaterFromShowtimes(movies);
                    onResult.onSuccess(theater, movies != null ? movies : new ArrayList<>());
                } else {
                    String errorMsg = response.body() != null ? response.body().getMessage() : "Failed to fetch theater movies";
                    onResult.onFailure(new Exception(errorMsg));
                }
            }

            @Override
            public void onFailure(Call<TheaterMoviesResponse> call, Throwable t) {
                onResult.onFailure(t);
            }
        });
    }

    private Theater extractTheaterFromShowtimes(List<TheaterMovie> movies) {
        if (movies == null || movies.isEmpty()) return null;

        // Lấy thông tin rạp từ showtime đầu tiên (giả sử tất cả showtimes thuộc cùng một rạp)
        Showtime showtime = movies.get(0).getShowtimes().get(0);
        if (showtime != null && showtime.getScreen() != null && showtime.getScreen().getTheater() != null) {
            return showtime.getScreen().getTheater();
        }
        return null;
    }
}