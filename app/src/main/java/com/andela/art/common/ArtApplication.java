package com.andela.art.common;

import android.app.Application;

import dagger.android.support.DaggerApplication;

/**
 * Created by zack on 3/5/18.
 */

public class ArtApplication extends Application {
    public ApplicationComponent applicationComponent;
    public ApplicationComponent applicationComponent(){
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        inject();
    }

    public void inject(){
        ArtApplication application = new ArtApplication();
        applicationComponent = DaggerApplicationComponent.builder().application(this).build();
    }

}
