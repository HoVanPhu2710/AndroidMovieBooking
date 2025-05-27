package com.client.androidmoviebooking.data.model.response;

import com.client.androidmoviebooking.domain.model.theater.Theater;
import com.client.androidmoviebooking.domain.model.theater.TheaterBrand;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TheaterResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("success")
    private Boolean success;
    @SerializedName("data")
    List<Theater> data;
    @SerializedName("total")
    private int total;
    @SerializedName("page")
    private int page;
    @SerializedName("size")
    private int size;

    public String getMessage() {return message;}
    public Boolean isSuccess() {return success;}

    public List<Theater> getData() {return data;}
    public int getTotal() {return total;}
    public int getPage() {return page;}
    public int getSize() {return size;}
}
