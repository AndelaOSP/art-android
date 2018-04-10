package com.andela.art.root;

import android.app.Application;

import com.andela.art.root.DaggerApplicationComponent;
import com.andela.art.login.LoginModule;

/**
 * Created by Mugiwara_Munyi on 28/02/2018.
 */

public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .loginModule(new LoginModule())
                .build();
    }

    /**
     * Get component.
     *
     * @return component - return the specified component.
     */
    public ApplicationComponent getComponent() {
        return component;
    }
}
