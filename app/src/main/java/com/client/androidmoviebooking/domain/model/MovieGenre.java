package com.client.androidmoviebooking.domain.model;

import com.google.gson.annotations.SerializedName;

public class MovieGenre {
    @SerializedName("id") private int id;
    @SerializedName("name") private String name;
    public String getName() { return name; }
}
