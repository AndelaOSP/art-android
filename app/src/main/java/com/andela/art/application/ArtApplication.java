package com.andela.art.application;

import android.app.Application;

import com.andela.art.injection.component.DaggerApplicationComponent;


/**
 * Created by godwingitonga on 08/03/2018.
 */

public class ArtApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initializeApplicationComponent();
    }

    private void initializeApplicationComponent() {
        DaggerApplicationComponent.builder();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
