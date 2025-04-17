package com.client.androidmoviebooking.data.model.response;

import com.client.androidmoviebooking.domain.model.RecommendMovie;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class RecommendMovieResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private DataWrapper data;

    @SerializedName("total")
    private int total;

    @SerializedName("page")
    private int page;

    @SerializedName("size")
    private int size;

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public DataWrapper getData() {
        return data;
    }

    public int getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public static class DataWrapper {
        @SerializedName("data")
        private Map<String, List<RecommendMovie>> data;

        public Map<String, List<RecommendMovie>> getData() {
            return data;
        }
    }
}