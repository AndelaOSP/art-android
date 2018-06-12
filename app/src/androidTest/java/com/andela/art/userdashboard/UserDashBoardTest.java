package com.andela.art.userdashboard;

import android.app.Activity;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;

import com.andela.art.R;
import com.andela.art.incidentreport.presentation.IncidentReportActivity;
import com.andela.art.userdashboard.presentation.UserDashBoardActivity;
import com.andela.art.utils.MockWebServerRule;
import com.andela.art.utils.RestServiceTestHelper;
import com.andela.art.utils.WaitActivityIsResumedIdlingResource;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import okhttp3.mockwebserver.MockResponse;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.andela.art.userdashboard.AssetsMapper.withCustomConstraints;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.core.IsNot.not;

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
    public MockWebServerRule mockWebServerRule = new MockWebServerRule();

    /**
     * Test User information is displayed appropriately to the user.
     */
    @Test
    public void testUserInfoIsDisplayed() {
        userDashboardActivityTestRule.launchActivity(null);

        onView(withId(R.id.email_address))
                .check(matches(allOf(isDisplayed(), withText("isaiah.kingori@andela.com"))));
        onView(withId(R.id.name))
                .check(matches(allOf(isDisplayed(),
                        withText("Isaiah King'ori"))
                ));
    }

    /**
     * Test new user can be able to swipe between multiple tabs to see different assets that they
     * have.
     * @throws Exception if an error occurs
     */
    @Test
    public void testUserCanSwipeToSeeDifferentAssets() throws Exception {
        String fileName = "multiple_assets_response.json";
        String asset = RestServiceTestHelper.
                getStringFromFile(getTargetContext(), fileName);
        mockWebServerRule.server.enqueue(new MockResponse().setBody(asset));
        userDashboardActivityTestRule.launchActivity(null);

        onView(withText("BSBSBS")).check(matches(isCompletelyDisplayed()));

        onView(withId(R.id.pager)).perform(withCustomConstraints(swipeLeft(),
                isDisplayingAtLeast(80)));

        onView(withText("KSKSKS")).check(matches(isCompletelyDisplayed()));

        onView(withId(R.id.pager)).perform(withCustomConstraints(swipeLeft(),
                isDisplayingAtLeast(80)));

        onView(withText("OPOPOP")).check(matches(isCompletelyDisplayed()));
    }

    /**
     * Test incidence report button does not appear when no assets are available.
     */
    @Test
    public void testIncidentReportButtonDoesNotAppearWhenNoAssetIsAvailable() {
        userDashboardActivityTestRule.launchActivity(null);
        onView(withId(R.id.incident_button))
                .check(matches(not(isDisplayed())));
    }
    /**
     * Test the incidence report button does appear when assets are available.
     * @throws Exception if an error occurs
     */
    @Test
    public void testIncidentReportButtonAppearsWhenAssetAreAvailable() throws Exception {
        String fileName = "double_asset_response.json";
        String asset = RestServiceTestHelper.
                getStringFromFile(getTargetContext(), fileName);
        mockWebServerRule.server.enqueue(new MockResponse().setBody(asset));
        userDashboardActivityTestRule.launchActivity(null);
        onView(withId(R.id.incident_button))
                .check(matches(isDisplayed()));
    }

    /**
     * Test clicking the report incidence button launches the IncidentReportActivity.
     * @throws Exception if an error occurs
     */
    @Test
    public void testReportIncidenceButtonClickLaunchesIncidentReportActivity() throws Exception {
        String fileName = "double_asset_response.json";
        String asset = RestServiceTestHelper.
                getStringFromFile(getTargetContext(), fileName);
        mockWebServerRule.server.enqueue(new MockResponse().setBody(asset));
        userDashboardActivityTestRule.launchActivity(null);

        onView(withId(R.id.incident_button)).perform(click());

        IdlingResource idlingResource = new WaitActivityIsResumedIdlingResource(
                IncidentReportActivity.class.getName());
        IdlingRegistry.getInstance().register(idlingResource);

        Assert.assertEquals(IncidentReportActivity.class,
                getCurrentActivity().getClass());
        IdlingRegistry.getInstance().unregister(idlingResource);
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
