package com.andela.art.root;

import android.app.Application;

/**
 * Created by Mugiwara_Munyi on 28/02/2018.
 */

public class App extends Application {

    private com.andela.art.root.ApplicationComponent mApplicationComponent;
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

    public com.andela.art.root.ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
