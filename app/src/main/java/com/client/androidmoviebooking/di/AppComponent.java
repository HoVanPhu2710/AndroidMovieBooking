package com.client.androidmoviebooking.di;

import com.client.androidmoviebooking.presentation.movie.detail.MovieDetailFragment;
import com.client.androidmoviebooking.presentation.movie.list.MovieListFragment;
import com.client.androidmoviebooking.presentation.theater.TheaterBrand.TheaterFragment;
import com.client.androidmoviebooking.presentation.theater.TheaterDetail.TheaterDetailFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        NetworkModule.class,
        RepositoryModule.class,
        ViewModelModule.class})
public interface AppComponent {
    void inject(MovieListFragment fragment);
    void inject(MovieDetailFragment fragment);
    void inject(TheaterFragment fragment);

    void inject(TheaterDetailFragment fragment);
}