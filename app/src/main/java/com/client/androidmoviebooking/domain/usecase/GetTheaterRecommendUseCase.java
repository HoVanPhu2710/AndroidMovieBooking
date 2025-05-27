package com.client.androidmoviebooking.domain.usecase;

import com.client.androidmoviebooking.data.api.ApiService;
import com.client.androidmoviebooking.data.model.response.TheaterResponse;
import com.client.androidmoviebooking.domain.model.theater.Theater;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetTheaterRecommendUseCase {
    private final ApiService apiService;

    public GetTheaterRecommendUseCase(ApiService apiService) {
        this.apiService = apiService;
    }
    public interface OnResult{
        void OnSuccess (List<Theater> theaterRecommends);
        void OnFailure (Throwable throwable);
    }

    public void execute(String city, GetTheaterRecommendUseCase.OnResult onResult){
        apiService.getTheaterRecommends(city).enqueue(new Callback<TheaterResponse>() {
            @Override
            public void onResponse(Call<TheaterResponse> call, Response<TheaterResponse> response) {
                if(response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<Theater> theaterRecommends = response.body().getData();
                    onResult.OnSuccess(theaterRecommends != null ? theaterRecommends : new ArrayList<>());
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
