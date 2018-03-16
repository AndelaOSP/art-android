package com.andela.art;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.andela.art.login.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by asce1062 on 22/02/2018.
 */

/**
 * Tests for the login screen, the main screen which contains a user is presented on first launch
 * of the application.
 */

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    /**
     * {@link ActivityTestRule} is a JUnit {@link Rule @Rule} to launch your activity under test.
     *
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class);

    /**
     * Test Signin button.
     *
     * @throws Exception if an error occurs
     */
    @Test
    public void clickSignInButton() throws Exception {
        // Click on the sign in with google button
        onView(withId(R.id.google_sign_in_button))
                .perform(click());
    }
}
