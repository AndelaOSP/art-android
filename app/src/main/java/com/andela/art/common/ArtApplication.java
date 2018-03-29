package com.andela.art.common;

import android.app.Application;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

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

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(android.app.Activity activity, Bundle bundle) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            @Override
            public void onActivityStarted(android.app.Activity activity) {
                // To do on activity started
            }

            @Override
            public void onActivityResumed(android.app.Activity activity) {
                // To do on activity resumed
            }

            @Override
            public void onActivityPaused(android.app.Activity activity) {
                // To do on activity paused
            }

            @Override
            public void onActivityStopped(android.app.Activity activity) {
                // To do on activity stopped
            }

            @Override
            public void onActivitySaveInstanceState(android.app.Activity activity, Bundle bundle) {
                // To do on activity saved instance state
            }

            @Override
            public void onActivityDestroyed(android.app.Activity activity) {
                // To do on activity destroyed
            }
        });
    }
}
