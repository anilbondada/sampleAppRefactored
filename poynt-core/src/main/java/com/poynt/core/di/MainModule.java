package com.poynt.core.di;

import android.app.Application;

import dagger.Module;

@Module
public class MainModule {

    private final Application application;

    public MainModule(Application application) {
        this.application = application;
    }

}
