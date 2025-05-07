package com.client.androidmoviebooking.domain.model.theater;

import com.google.gson.annotations.SerializedName;

public class TheaterBrand {
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("logo")
    private String logo;

    public TheaterBrand(Integer id, String name, String logo) {
        this.id = id;
        this.name = name;
        this.logo = logo;
    }
    public Integer getId() {return id;}

    public String getName() {return name;}
    public String getLogo() {return logo;}
}
