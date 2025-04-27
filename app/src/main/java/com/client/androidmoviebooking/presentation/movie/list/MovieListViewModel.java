package com.client.androidmoviebooking.presentation.movie.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.client.androidmoviebooking.domain.model.movie.Movie;
import com.client.androidmoviebooking.domain.model.movie.RecommendMovie;
import com.client.androidmoviebooking.domain.usecase.GetMovieListUseCase;
import com.client.androidmoviebooking.domain.usecase.GetRecommendedMovieListUseCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class MovieListViewModel extends ViewModel {
    private final GetMovieListUseCase getMovieListUseCase;
    private final GetRecommendedMovieListUseCase getRecommendedMovieListUseCase;
    private final MutableLiveData<List<Movie>> popularMovies = new MutableLiveData<>();
    private final MutableLiveData<Map<String, List<RecommendMovie>>> moviesByGenre = new MutableLiveData<>();

    @Inject
    public MovieListViewModel(GetMovieListUseCase getMovieListUseCase, GetRecommendedMovieListUseCase getRecommendedMovieListUseCase) {
        this.getMovieListUseCase = getMovieListUseCase;
        this.getRecommendedMovieListUseCase = getRecommendedMovieListUseCase;
    }

    public LiveData<List<Movie>> getPopularMovies() {
        return popularMovies;
    }

    public LiveData<Map<String, List<RecommendMovie>>> getMoviesByGenre() {
        return moviesByGenre;
    }

    public void loadMovies() {
        getMovieListUseCase.execute(new GetMovieListUseCase.OnResult() {
            @Override
            public void onSuccess(List<Movie> popularMovies) {
                MovieListViewModel.this.popularMovies.postValue(popularMovies != null ? popularMovies : new ArrayList<>());
            }

            @Override
            public void onFailure(Throwable throwable) {
                popularMovies.postValue(new ArrayList<>());
            }
        });

        getRecommendedMovieListUseCase.execute(new GetRecommendedMovieListUseCase.OnResult() {
            @Override
            public void onSuccess(Map<String, List<RecommendMovie>> moviesByGenre) {
                MovieListViewModel.this.moviesByGenre.postValue(moviesByGenre != null ? moviesByGenre : new HashMap<>());
            }

            @Override
            public void onFailure(Throwable throwable) {
                moviesByGenre.postValue(new HashMap<>());
            }
        });
    }
}