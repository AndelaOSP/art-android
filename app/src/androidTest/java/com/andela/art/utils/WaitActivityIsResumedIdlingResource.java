package com.andela.art.utils;

import android.app.Activity;
import android.support.test.espresso.IdlingResource;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitor;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;

import java.util.Collection;

/**
 * Created by zack on 4/12/18.
 */
public class WaitActivityIsResumedIdlingResource implements IdlingResource {
    private final ActivityLifecycleMonitor instance;
    private final String activityToWaitClassName;
    private volatile ResourceCallback resourceCallback;
    boolean resumed = false;

    /**
     *
     * @param activityToWaitClassName - activity that test will wait to run.
     */
    public WaitActivityIsResumedIdlingResource(String activityToWaitClassName) {
        instance = ActivityLifecycleMonitorRegistry.getInstance();
        this.activityToWaitClassName = activityToWaitClassName;
    }

    /**
     * Get the clas name.
     * @return class name
     */
    @Override
    public String getName() {
        return this.getClass().getName();
    }

    /**
     * Check if the resource is idle.
     * @return resumed - whether the activity has started
     */
    @Override
    public boolean isIdleNow() {
        resumed = isActivityLaunched();
        if (resumed && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }

        return resumed;
    }

    /**
     * Check if an activity is launched or not.
     * @return boolean - boolean checking if an activity is launched or not.
     */
    private boolean isActivityLaunched() {
        Collection<Activity> activitiesInStage = instance.getActivitiesInStage(Stage.RESUMED);
        for (Activity activity : activitiesInStage) {
            if (activity.getClass().getName().equals(activityToWaitClassName)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param resourceCallback - resourceCallback
     */
    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }
}
