package com.client.androidmoviebooking.di;

import com.client.androidmoviebooking.data.api.ApiService;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        // Tạo HttpLoggingInterceptor để log các yêu cầu API
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Tạo Interceptor để ngăn mã hóa query parameter
        Interceptor queryInterceptor = chain -> {
            Request originalRequest = chain.request();
            HttpUrl originalUrl = originalRequest.url();

            // Tạo URL mới với query parameter không mã hóa
            HttpUrl.Builder urlBuilder = originalUrl.newBuilder();
            for (String name : originalUrl.queryParameterNames()) {
                for (String value : originalUrl.queryParameterValues(name)) {
                    urlBuilder.removeAllQueryParameters(name);
                    urlBuilder.addQueryParameter(name, value); // Thêm lại query không mã hóa
                }
            }
            HttpUrl newUrl = urlBuilder.build();

            // Tạo request mới với URL đã sửa
            Request newRequest = originalRequest.newBuilder()
                    .url(newUrl)
                    .build();

            return chain.proceed(newRequest);
        };

        // Tạo OkHttpClient và thêm các interceptor
        return new OkHttpClient.Builder()
                .addInterceptor(queryInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}