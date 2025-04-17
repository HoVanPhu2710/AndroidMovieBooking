package com.client.androidmoviebooking.di;

import androidx.lifecycle.ViewModel;

import com.client.androidmoviebooking.presentation.movie.list.MovieListViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel.class)
    abstract ViewModel bindMovieListViewModel(MovieListViewModel viewModel);
}