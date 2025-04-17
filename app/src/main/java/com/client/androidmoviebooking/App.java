package com.client.androidmoviebooking;

import android.app.Application;

import com.client.androidmoviebooking.di.AppComponent;
import com.client.androidmoviebooking.di.AppModule;
import com.client.androidmoviebooking.di.DaggerAppComponent;

public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}