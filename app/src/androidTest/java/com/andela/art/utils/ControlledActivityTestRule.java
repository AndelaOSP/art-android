package com.andela.art.utils;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;

/**
 * Controlled Activity.
 * @param <T>
 */
public class ControlledActivityTestRule<T extends Activity> extends ActivityTestRule<T> {

    /**
     * Constructor method.
     * @param activityClass - activityClass
     */
    public ControlledActivityTestRule(Class<T> activityClass) {
        super(activityClass, false);
    }

}
