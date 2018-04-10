package com.andela.art.common;

import android.app.Application;

/**
 * Created by zack on 3/5/18.
 */

public class ArtApplication extends Application {
    public ApplicationComponent applicationComponent;

    /**
     *
     * @return Application component.
     */
    public ApplicationComponent applicationComponent() {
        return applicationComponent;
    }

    /**
     * Application onCreate method.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .application(this)
                .build();

    }
}
