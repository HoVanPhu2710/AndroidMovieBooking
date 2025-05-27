package com.client.androidmoviebooking.domain.usecase;

import com.client.androidmoviebooking.data.api.ApiService;
import com.client.androidmoviebooking.data.model.response.DateTimeResponse;
import com.client.androidmoviebooking.domain.model.theater.DateItem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetDateTimeUseCase {
    private final ApiService apiService;

    public GetDateTimeUseCase(ApiService apiService) {
        this.apiService = apiService;
    }

    public interface OnResult {
        void onSuccess(List<DateItem> dates);
        void onFailure(Throwable throwable);
    }

    public void execute(int days, OnResult onResult) {
        apiService.getTheaterDays(days).enqueue(new Callback<DateTimeResponse>() {
            @Override
            public void onResponse(Call<DateTimeResponse> call, Response<DateTimeResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<DateItem> apiDates = response.body().getData();
                    List<DateItem> dateItems = new ArrayList<>();

                    LocalDate today = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

                    for (int i = 0; i < apiDates.size(); i++) {
                        DateItem apiDate = apiDates.get(i);
                        LocalDate date = today.plusDays(i);
                        String fullDate = date.format(formatter);
                        dateItems.add(new DateItem(apiDate.getLabel(), apiDate.getDay(), fullDate));
                    }

                    onResult.onSuccess(dateItems != null ? dateItems : new ArrayList<>());
                } else {
                    onResult.onFailure(new Exception("Failed to fetch theater days"));
                }
            }

            @Override
            public void onFailure(Call<DateTimeResponse> call, Throwable t) {
                onResult.onFailure(t);
            }
        });
    }
}