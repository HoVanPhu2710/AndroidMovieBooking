package com.client.androidmoviebooking.data.model.response;

import com.client.androidmoviebooking.domain.model.movie.MovieDetail;
import com.google.gson.annotations.SerializedName;

public class MovieDetailResponse {
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

    public static class DataWrapper {
        @SerializedName("data")
        private MovieDetail data;

        public MovieDetail getData() {
            return data;
        }
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public MovieDetail getData() {
        return data != null ? data.getData() : null;
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
}