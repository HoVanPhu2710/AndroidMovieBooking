package com.client.androidmoviebooking.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.client.androidmoviebooking.presentation.movie.detail.MovieDetailViewModel;
import com.client.androidmoviebooking.presentation.movie.list.MovieListViewModel;
import com.client.androidmoviebooking.presentation.theater.TheaterBrand.TheaterViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel.class)
    abstract ViewModel bindMovieListViewModel(MovieListViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel.class)
    abstract ViewModel bindMovieDetailViewModel(MovieDetailViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TheaterViewModel.class)
    abstract ViewModel bindTheaterViewModel(TheaterViewModel viewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}