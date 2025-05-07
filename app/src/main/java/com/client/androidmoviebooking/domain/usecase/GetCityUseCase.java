package com.client.androidmoviebooking.domain.usecase;

import com.client.androidmoviebooking.data.api.ApiService;
import com.client.androidmoviebooking.data.model.response.CityResponse;
import com.client.androidmoviebooking.data.model.response.TheaterResponse;
import com.client.androidmoviebooking.domain.model.theater.Theater;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCityUseCase {
    private final ApiService apiService;

    public GetCityUseCase(ApiService apiService) {
        this.apiService = apiService;
    }
    public interface OnResult{
        void OnSuccess (List<String> cities);
        void OnFailure (Throwable throwable);
    }

    public void execute(GetCityUseCase.OnResult onResult){
        apiService.getCities().enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if(response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<String> cities = response.body().getData();
                    onResult.OnSuccess(cities != null ? cities : new ArrayList<>());
                }else {
                    onResult.OnFailure(new Exception("Theater API call Failed"));
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {

            }
        });
    }
}
