package com.client.androidmoviebooking.domain.repository;

import com.client.androidmoviebooking.domain.model.Cast;
import com.client.androidmoviebooking.domain.model.MovieCast;
import com.client.androidmoviebooking.domain.model.PersonInMovie;

import java.util.List;

public class CastItem implements PersonInMovie {
    private final MovieCast movieCast;

    public CastItem(MovieCast movieCast) {
        this.movieCast = movieCast;
    }

    public MovieCast getCast() {
        return movieCast;
    }

    @Override
    public int getViewType() {
        return 1;
    }
}

