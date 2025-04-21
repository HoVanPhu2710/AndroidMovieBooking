package com.client.androidmoviebooking.domain.model;

import com.google.gson.annotations.SerializedName;

public class MovieCast {
    @SerializedName("cast")
    private Cast cast;
    @SerializedName("characterName")
    private String characterName;

    public Cast getCast() {
        return cast;
    }

    public String getCharacterName() {
        return characterName;
    }
}