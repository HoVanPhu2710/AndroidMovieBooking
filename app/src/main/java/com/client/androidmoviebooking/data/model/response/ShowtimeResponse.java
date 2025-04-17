package com.client.androidmoviebooking.data.model.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;
public class ShowtimeResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("movieId")
    private int movieId;

    @SerializedName("screenId")
    private int screenId;

    @SerializedName("movieTitle")
    private String movieTitle;

    @SerializedName("screenNumber")
    private String screenNumber;

    @SerializedName("theaterName")
    private String theaterName;

    @SerializedName("startTime")
    private String startTime;

    @SerializedName("endTime")
    private String endTime;

    //Getter và setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getMovieId() { return movieId; }
    public void setMovieId(int movieId) { this.movieId = movieId; }
    public int getScreenId() { return screenId; }
    public void setScreenId(int screenId) { this.screenId = screenId; }
    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
    public String getScreenNumber() { return screenNumber; }
    public void setScreenNumber(String screenNumber) { this.screenNumber = screenNumber; }
    public String getTheaterName() { return theaterName; }
    public void setTheaterName(String theaterName) { this.theaterName = theaterName; }
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
}
