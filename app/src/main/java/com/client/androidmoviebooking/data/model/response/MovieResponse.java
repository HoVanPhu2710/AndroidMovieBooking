package com.client.androidmoviebooking.data.model.response;

import com.client.androidmoviebooking.domain.model.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
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
        @SerializedName("pagination")
        private Pagination pagination;

        @SerializedName("data")
        private List<Movie> data;

        public Pagination getPagination() {
            return pagination;
        }

        public List<Movie> getData() {
            return data;
        }
    }

    public static class Pagination {
        @SerializedName("size")
        private int size;

        @SerializedName("totalPages")
        private int totalPages;

        @SerializedName("page")
        private int page;

        @SerializedName("totalElements")
        private int totalElements;

        public int getSize() {
            return size;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public int getPage() {
            return page;
        }

        public int getTotalElements() {
            return totalElements;
        }
    }
}