package com.client.androidmoviebooking.domain.model;

import com.google.gson.annotations.SerializedName;

public class RecommendMovieGenre {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    public String getName() {return name;}
}
