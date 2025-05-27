package com.client.androidmoviebooking.data.model.response;

import com.client.androidmoviebooking.domain.model.theater.Theater;
import com.client.androidmoviebooking.domain.model.theater.TheaterMovie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TheaterMoviesResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<TheaterMovie> data;

    @SerializedName("total")
    private int total;

    @SerializedName("page")
    private int page;

    @SerializedName("size")
    private int size;

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<TheaterMovie> getData() {
        return data;
    }

    public int getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}