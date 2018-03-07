package com.andela.art.common;

import android.app.Application;

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
        applicationComponent =  DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .dataModule(new DataModule())
                .build();
    }

}
