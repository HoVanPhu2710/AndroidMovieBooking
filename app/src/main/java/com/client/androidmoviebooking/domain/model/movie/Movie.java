package com.client.androidmoviebooking.domain.model.movie;

import com.client.androidmoviebooking.presentation.movie.list.MovieListAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Movie implements MovieListAdapter.MovieItem {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("duration")
    private int duration;

    @SerializedName("releaseDate")
    private String releaseDate;

    @SerializedName("description")
    private String description;

    @SerializedName("director")
    private MovieDirector director;

    @SerializedName("trailerUrl")
    private String trailerUrl;

    @SerializedName("englishTitle")
    private String englishTitle;

    @SerializedName("isAvailable")
    private boolean isAvailable;

    @SerializedName("posterUrl")
    private String posterUrl;

    @SerializedName("rating")
    private float rating;

    @SerializedName("genres")
    private List<MovieGenre> genres;

    @SerializedName("slug")
    private String slug;

    @SerializedName("casts")
    private List<MovieCast> casts;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getPosterUrl() {
        return posterUrl;
    }

    @Override
    public float getRating() {
        return rating;
    }

    @Override
    public List<String> getGenreNames() {
        List<String> genreNames = new ArrayList<>();
        if (genres != null) {
            for (MovieGenre genre : genres) {
                genreNames.add(genre.getName());
            }
        }
        return genreNames;
    }

    public int getDuration() {
        return duration;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public MovieDirector getDirector() {
        return director;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public String getEnglishTitle() {
        return englishTitle;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public String getSlug() {
        return slug;
    }

    public List<MovieCast> getCasts() {return casts;}
}