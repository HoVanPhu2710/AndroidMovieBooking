package com.client.androidmoviebooking.domain.usecase;

import com.client.androidmoviebooking.data.api.ApiService;
import com.client.androidmoviebooking.data.model.response.TheaterBrandResponse;
import com.client.androidmoviebooking.domain.model.theater.TheaterBrand;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetTheaterBrandUseCase {
    private final ApiService apiService;

    public GetTheaterBrandUseCase(ApiService apiService) {
        this.apiService = apiService;
    }
    public interface OnResult{
        void onSuccess(List<TheaterBrand> theaterBrands);
        void onFailure(Throwable throwable);
    }
    public void execute(String city, OnResult onResult){
        apiService.getTheaterBrands(city).enqueue(new Callback<TheaterBrandResponse>() {
            @Override
            public void onResponse(Call<TheaterBrandResponse> call, Response<TheaterBrandResponse> response) {
                if(response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<TheaterBrand> theaterBrands = response.body().getData();
                    onResult.onSuccess(theaterBrands != null ? theaterBrands : new ArrayList<>());
                }else {
                    onResult.onFailure(new Exception("Brand API call failed"));
                }
            }

            @Override
            public void onFailure(Call<TheaterBrandResponse> call, Throwable t) {

            }
        });
    }
}
