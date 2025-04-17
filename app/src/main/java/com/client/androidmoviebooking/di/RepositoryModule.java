package com.client.androidmoviebooking.di;

import com.client.androidmoviebooking.data.api.ApiService;
import com.client.androidmoviebooking.domain.usecase.GetMovieListUseCase;
import com.client.androidmoviebooking.domain.usecase.GetRecommendedMovieListUseCase;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class RepositoryModule {
    @Provides
    @Singleton
    GetMovieListUseCase provideGetMovieListUseCase(ApiService apiService) {
        return new GetMovieListUseCase(apiService);
    }

    @Provides
    @Singleton
    GetRecommendedMovieListUseCase provideGetRecommendedMovieListUseCase(ApiService apiService) {
        return new GetRecommendedMovieListUseCase(apiService);
    }
}