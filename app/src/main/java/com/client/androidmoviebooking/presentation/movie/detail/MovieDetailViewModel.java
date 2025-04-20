package com.client.androidmoviebooking.presentation.movie.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.client.androidmoviebooking.domain.model.Movie;
import com.client.androidmoviebooking.domain.usecase.GetMovieDetailUseCase;

import javax.inject.Inject;

public class MovieDetailViewModel extends ViewModel {
    private final GetMovieDetailUseCase getMovieDetailUseCase;
    private final MutableLiveData<Movie> movie = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    @Inject
    public MovieDetailViewModel(GetMovieDetailUseCase getMovieDetailUseCase) {
        this.getMovieDetailUseCase = getMovieDetailUseCase;
    }

    public LiveData<Movie> getMovie() {
        return movie;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void loadMovieDetail(int movieId) {
        getMovieDetailUseCase.execute(movieId, new GetMovieDetailUseCase.OnResult() {
            @Override
            public void onSuccess(Movie movieData) {
                movie.postValue(movieData);
            }

            @Override
            public void onFailure(Throwable throwable) {
                error.postValue(throwable.getMessage());
            }
        });
    }
}