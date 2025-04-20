package com.client.androidmoviebooking.domain.model;

import com.google.gson.annotations.SerializedName;

public class MovieCast {
    @SerializedName("name")
    private String name;

    @SerializedName("avatarUrl")
    private String avatarUrl;

    @SerializedName("characterName")
    private String characterName;

    public String getName() {
        return name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getCharacterName() {
        return characterName;
    }
}
