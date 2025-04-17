package com.client.androidmoviebooking.di;

import com.client.androidmoviebooking.presentation.movie.list.MovieListFragment;

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
}