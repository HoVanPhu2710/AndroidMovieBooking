package com.client.androidmoviebooking.presentation.movie.detail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.client.androidmoviebooking.domain.model.movie.MovieDetail;
import com.client.androidmoviebooking.domain.usecase.GetMovieDetailUseCase;

import javax.inject.Inject;

public class MovieDetailViewModel extends ViewModel {
    private final GetMovieDetailUseCase getMovieDetailUseCase;
    private final MutableLiveData<MovieDetail> movieDetail = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    @Inject
    public MovieDetailViewModel(GetMovieDetailUseCase getMovieDetailUseCase) {
        this.getMovieDetailUseCase = getMovieDetailUseCase;
    }

    public LiveData<MovieDetail> getMovieDetail() {
        return movieDetail;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void loadMovieDetail(String movieSlug) {
        Log.d("TAG", "loadMovieDetail: ");
        getMovieDetailUseCase.execute(movieSlug, new GetMovieDetailUseCase.OnResult() {

            @Override
            public void onSuccess(MovieDetail movieData) {
                Log.d("TAG", "onSuccess: ");
                movieDetail.postValue(movieData);

            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d("TAG", "onFailure: ");
                error.postValue(throwable.getMessage());
            }
        });
    }
}