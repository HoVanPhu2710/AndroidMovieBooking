package com.client.androidmoviebooking.di;

import com.client.androidmoviebooking.data.api.ApiService;
import com.client.androidmoviebooking.domain.usecase.GetCityUseCase;
import com.client.androidmoviebooking.domain.usecase.GetDateTimeUseCase;
import com.client.androidmoviebooking.domain.usecase.GetMovieListUseCase;
import com.client.androidmoviebooking.domain.usecase.GetRecommendedMovieListUseCase;
import com.client.androidmoviebooking.domain.usecase.GetTheaterBrandUseCase;
import com.client.androidmoviebooking.domain.usecase.GetTheaterMoviesUseCase;
import com.client.androidmoviebooking.domain.usecase.GetTheaterRecommendUseCase;
import com.client.androidmoviebooking.domain.usecase.GetTheaterUseCase;

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

    @Provides
    @Singleton
    GetCityUseCase provideGetCityUseCase(ApiService apiService) {
        return new GetCityUseCase(apiService);
    }

    @Provides
    @Singleton
    GetTheaterBrandUseCase provideGetTheaterBrandUseCase(ApiService apiService) {
        return new GetTheaterBrandUseCase(apiService);
    }

    @Provides
    @Singleton
    GetTheaterUseCase provideGetTheaterUseCase(ApiService apiService) {
        return new GetTheaterUseCase(apiService);
    }

    @Provides
    @Singleton
    GetTheaterRecommendUseCase provideGetTheaterRecommendUseCase(ApiService apiService) {
        return new GetTheaterRecommendUseCase(apiService);
    }

    @Provides
    @Singleton
    GetDateTimeUseCase provideGetDateTimeUseCase(ApiService apiService) {
        return new GetDateTimeUseCase(apiService);
    }

    @Provides
    @Singleton
    GetTheaterMoviesUseCase provideGetTheaterMoviesUseCase(ApiService apiService) {
        return new GetTheaterMoviesUseCase(apiService);
    }
}