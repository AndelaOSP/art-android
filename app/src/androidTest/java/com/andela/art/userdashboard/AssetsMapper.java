package com.andela.art.userdashboard;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import org.hamcrest.Matcher;

/**
 * AssetsMapper.
 */
public class AssetsMapper {
    /**
     * Custom view action to allow use of isDisplayingAtLeast while writing tests.
     * @param action action
     * @param constraints constraints
     * @return ViewAction
     */
    public static ViewAction withCustomConstraints(final ViewAction action, final Matcher<View>
            constraints) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return constraints;
            }

            @Override
            public String getDescription() {
                return action.getDescription();
            }

            @Override
            public void perform(UiController uiController, View view) {
                action.perform(uiController, view);
            }
        };
    }
}
