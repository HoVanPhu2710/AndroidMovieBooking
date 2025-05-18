package com.client.androidmoviebooking.domain.model.theater;

import com.google.gson.annotations.SerializedName;

public class Screen {
    @SerializedName("id")
    private int id;

    @SerializedName("screenNumber")
    private String screenNumber;

    @SerializedName("totalSeats")
    private int totalSeats;

    @SerializedName("theater")
    private Theater theater;

    // Getters
    public int getId() {
        return id;
    }

    public String getScreenNumber() {
        return screenNumber;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public Theater getTheater() {
        return theater;
    }
}
