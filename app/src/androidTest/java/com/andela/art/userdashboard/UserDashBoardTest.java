package com.andela.art.userdashboard;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;

import com.andela.art.R;
import com.andela.art.userdashboard.presentation.UserDashBoardActivity;
import com.andela.art.utils.MockWebServerRule;
import com.andela.art.utils.OkHttpIdlingResourceRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Test for UserDashBoard.
 */

@RunWith(AndroidJUnit4.class)
public class UserDashBoardTest {

    @Rule
    public ActivityTestRule<UserDashBoardActivity> userDashboardActivityTestRule =
            new ActivityTestRule<>(UserDashBoardActivity.class, true,
                    false);

    @Rule
    public OkHttpIdlingResourceRule okHttpIdlingResourceRule = new OkHttpIdlingResourceRule();

    @Rule
    public MockWebServerRule mockWebServerRule = new MockWebServerRule();

    /**
     * Test User information is displayed appropriately to the user.
     */
    @Test
    public void testUserInfoIsDisplayed() {
        userDashboardActivityTestRule.launchActivity(null);

        onView(withId(R.id.email_address))
                .check(matches(allOf(isDisplayed(), withText("philip.kalela@andela.com"))));
        onView(withId(R.id.name))
                .check(matches(allOf(isDisplayed(),
                        withText("Philip Kalela"))
                ));
    }

    /**
     * Get a current activity.
     * @return activity
     */
    public Activity getCurrentActivity() {
        final Activity[] currentActivity = new Activity[1];
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection<Activity> resumedActivities = ActivityLifecycleMonitorRegistry
                        .getInstance().getActivitiesInStage(Stage.RESUMED);
                for (Activity act : resumedActivities) {
                    currentActivity[0] = act;
                    break;
                }
            }
        });

        return currentActivity[0];
    }

}
