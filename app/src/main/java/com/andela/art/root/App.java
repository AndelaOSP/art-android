package com.andela.art.root;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

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

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                // To do on activity started
            }

            @Override
            public void onActivityResumed(Activity activity) {
                // To do on activity resumed
            }

            @Override
            public void onActivityPaused(Activity activity) {
                // To do on activity paused
            }

            @Override
            public void onActivityStopped(Activity activity) {
                // To do on activity stopped
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                // To do on activity saved instance state
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                // To do on activity destroyed
            }
        });
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
