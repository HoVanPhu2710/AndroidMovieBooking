package com.client.androidmoviebooking.domain.usecase;

import com.client.androidmoviebooking.data.api.ApiService;
import com.client.androidmoviebooking.data.model.response.TheaterResponse;
import com.client.androidmoviebooking.domain.model.theater.Theater;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetTheaterUseCase {
    private final ApiService apiService;

    public GetTheaterUseCase(ApiService apiService) {
        this.apiService = apiService;
    }
    public interface OnResult{
        void OnSuccess (List<Theater> theaters);
        void OnFailure (Throwable throwable);
    }

    public void execute(int theaterBrandId, String city, OnResult onResult){
        apiService.getTheaters(theaterBrandId, city).enqueue(new Callback<TheaterResponse>() {
            @Override
            public void onResponse(Call<TheaterResponse> call, Response<TheaterResponse> response) {
                if(response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<Theater> theaters = response.body().getData();
                    onResult.OnSuccess(theaters != null ? theaters : new ArrayList<>());
                }else {
                    onResult.OnFailure(new Exception("Theater API call Failed"));
                }
            }

            @Override
            public void onFailure(Call<TheaterResponse> call, Throwable t) {

            }
        });
    }
}
