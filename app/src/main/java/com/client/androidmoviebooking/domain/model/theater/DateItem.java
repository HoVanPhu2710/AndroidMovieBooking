package com.client.androidmoviebooking.domain.model.theater;

import com.google.gson.annotations.SerializedName;

public class DateItem {
    @SerializedName("label")
    private String label;

    @SerializedName("day")
    private String day;

    private String date;

    public DateItem(String label, String day, String date) {
        this.label = label;
        this.day = day;
        this.date = date;
    }

    public String getLabel() { return label; }
    public String getDay() { return day; }
    public String getDate() { return date; }
}