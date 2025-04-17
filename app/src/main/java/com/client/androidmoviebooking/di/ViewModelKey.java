package com.client.androidmoviebooking.di;

import androidx.lifecycle.ViewModel;

import dagger.MapKey;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@MapKey
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ViewModelKey {
    Class<? extends ViewModel> value();
}