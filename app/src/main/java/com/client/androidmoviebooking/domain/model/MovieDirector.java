package com.client.androidmoviebooking.domain.model;

import com.google.gson.annotations.SerializedName;

public class MovieDirector {
    @SerializedName("id") private int id;
    @SerializedName("name") private String name;
    @SerializedName("avatar") private String avatar;
    public String getName() { return name; }
}
