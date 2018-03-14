package com.andela.art.application;

import android.app.Application;

import com.andela.art.injection.component.ApplicationComponent;
import com.andela.art.injection.component.DaggerApplicationComponent;
import com.andela.art.injection.modules.ApplicationModule;


/**
 * Created by godwingitonga on 08/03/2018.
 */

public class ArtApplication extends Application {
    private ApplicationComponent mApplicationComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        initializeApplicationComponent();
    }

    private void initializeApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule("http://10.0.2.2:3000", this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
