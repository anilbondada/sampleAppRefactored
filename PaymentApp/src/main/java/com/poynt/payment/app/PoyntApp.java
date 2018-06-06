package com.poynt.payment.app;

import android.app.Application;

import com.poynt.core.di.CoreComponent;

public class PoyntApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CoreComponent.init(this);
    }
}
