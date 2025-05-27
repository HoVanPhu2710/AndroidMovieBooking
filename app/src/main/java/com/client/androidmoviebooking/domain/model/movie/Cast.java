package com.client.androidmoviebooking.domain.model.movie;

import com.google.gson.annotations.SerializedName;

public class Cast {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("avatar")
    private String avatar;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }
}
