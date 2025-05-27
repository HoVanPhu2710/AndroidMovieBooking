package com.client.androidmoviebooking.domain.model.theater;

import com.google.gson.annotations.SerializedName;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Showtime {
    @SerializedName("id")
    private int id;

    @SerializedName("startTime")
    private String startTime;

    @SerializedName("endTime")
    private String endTime;

    @SerializedName("screen")
    private Screen screen;

    @SerializedName("availableSeats")
    private String availableSeats;

    // Formatter để chỉ lấy giờ và phút
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    // Getters
    public int getId() {
        return id;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Screen getScreen() {
        return screen;
    }

    public String getAvailableSeats() {
        return availableSeats;
    }

    public String getFormattedStartTime() {
        if (startTime == null || startTime.isEmpty()) return "";
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(startTime);
            return zonedDateTime.format(TIME_FORMATTER); // Trả về "HH:mm", ví dụ: "19:00"
        } catch (Exception e) {
            return startTime; // Trả về nguyên gốc nếu lỗi
        }
    }

    public String getFormattedEndTime() {
        if (endTime == null || endTime.isEmpty()) return "";
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(endTime);
            return zonedDateTime.format(TIME_FORMATTER); // Trả về "HH:mm", ví dụ: "20:45"
        } catch (Exception e) {
            return endTime; // Trả về nguyên gốc nếu lỗi
        }
    }

    public String getTimeRange() {
        return getFormattedStartTime() + " - " + getFormattedEndTime(); // Trả về "19:00 - 20:45"
    }

    public int getTotalSeats() {
        return (screen != null) ? screen.getTotalSeats() : 0;
    }

    public String getSeatsInfo() {
        int totalSeats = (screen != null) ? screen.getTotalSeats() : 0;
        return "Còn " + (availableSeats != null ? availableSeats : "0") + "/" + totalSeats;
    }
}