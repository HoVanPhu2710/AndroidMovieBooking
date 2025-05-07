package com.client.androidmoviebooking.domain.model.theater;

import com.google.gson.annotations.SerializedName;

public class Theater {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("address")
    private String address;
    @SerializedName("city")
    private String city;
    @SerializedName("totalScreens")
    private int totalScreens;

    @SerializedName("brand")
    private String brand;

    @SerializedName("theaterBrandId")
    private Integer theaterBrandId;

    @SerializedName("logo")
    private String logo;

    public Theater(int id, String name, String address, String city, int totalScreens, String brand, Integer theaterBrandId, String logo){
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.totalScreens = totalScreens;
        this.brand = brand;
        this.theaterBrandId = theaterBrandId;
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public int getTotalScreens() {
        return totalScreens;
    }

    public Integer gettheaterBrandId() {
        return theaterBrandId;
    }

    public String getBrand(){
        return brand;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
