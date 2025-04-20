package com.client.androidmoviebooking.domain.repository;

import com.client.androidmoviebooking.domain.model.MovieCast;
import com.client.androidmoviebooking.domain.model.PersonInMovie;

public class CastItem implements PersonInMovie {
    private final MovieCast cast;

    public CastItem(MovieCast cast) {
        this.cast = cast;
    }

    public MovieCast getCast() {
        return cast;
    }

    @Override
    public int getViewType() {
        return 1;
    }
}

