package com.client.androidmoviebooking.domain.repository;

import com.client.androidmoviebooking.domain.model.movie.MovieDirector;
import com.client.androidmoviebooking.domain.model.movie.PersonInMovie;

public class DirectorItem implements PersonInMovie {
    private final MovieDirector director;

    public DirectorItem(MovieDirector director) {
        this.director = director;
    }

    public MovieDirector getDirector() {
        return director;
    }

    @Override
    public int getViewType() {
        return 0;
    }
}
