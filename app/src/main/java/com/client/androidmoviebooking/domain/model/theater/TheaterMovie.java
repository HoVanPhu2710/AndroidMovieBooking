package com.client.androidmoviebooking.domain.model.theater;

import com.client.androidmoviebooking.domain.model.movie.MovieGenre;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TheaterMovie {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("posterUrl")
    private String posterUrl;

    @SerializedName("rating")
    private double rating;

    @SerializedName("duration")
    private int duration;

    @SerializedName("genres")
    private List<MovieGenre> genres;

    @SerializedName("showtimes")
    private List<Showtime> showtimes;

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public double getRating() {
        return rating;
    }

    public int getDuration() {
        return duration;
    }

    public List<MovieGenre> getGenres() {
        return genres;
    }

    public List<Showtime> getShowtimes() {
        return showtimes;
    }

    public String getGenresAsString() {
        if (genres == null || genres.isEmpty()) return "";
        StringBuilder genresStr = new StringBuilder();
        for (int i = 0; i < genres.size(); i++) {
            genresStr.append(genres.get(i).getName());
            if (i < genres.size() - 1) genresStr.append(", ");
        }
        return genresStr.toString();
    }
}