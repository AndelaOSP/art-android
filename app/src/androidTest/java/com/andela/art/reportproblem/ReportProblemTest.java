package com.andela.art.reportproblem;

import android.app.Activity;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;

import com.andela.art.R;
import com.andela.art.reportproblem.presentation.ReportProblemActivity;
import com.andela.art.settings.SettingsActivity;
import com.andela.art.utils.ControlledActivityTestRule;
import com.andela.art.utils.MockWebServerRule;
import com.andela.art.utils.OkHttpIdlingResourceRule;
import com.andela.art.utils.RestServiceTestHelper;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import okhttp3.mockwebserver.MockResponse;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.not;

/**
 * Test Report a Problem Activity.
 */
@RunWith(AndroidJUnit4.class)
public class ReportProblemTest {

    @Rule
    public IntentsTestRule<ReportProblemActivity> activityTestRule =
            new IntentsTestRule<>(ReportProblemActivity.class,  true, true);

    @Rule
    public OkHttpIdlingResourceRule okHttpIdlingResource = new OkHttpIdlingResourceRule();

    @Rule
    public MockWebServerRule mockWebServerRule = new MockWebServerRule();

    @Rule
    public ControlledActivityTestRule controlledActivity =
            new ControlledActivityTestRule(SettingsActivity.class);

    /**
     * Test closing of the report a problem activity.
     */
    @Test
    public void testCloseOfReportProblemActivity() {
        onView(withId(R.id.close_report_button)).check(matches(isDisplayed()));
        onView(withId(R.id.close_report_button)).perform(click());
        assertEquals(SettingsActivity.class, getCurrentActivity().getClass());
    }

    /**
     * Test display of the toolbar on the activity.
     */
    @Test
    public void testToolbarIsDisplayed() {
        onView(withText("Report a Problem")).check(matches(isDisplayed()));
    }


    /**
     * Test report sending.
     * @throws Exception if an error occurs
     */
    @Test
    public void testReportSending() throws Exception {
        String fileName = "report_problem_response.json";
        String asset = RestServiceTestHelper.
                getStringFromFile(getTargetContext(), fileName);
        mockWebServerRule.server.enqueue(new MockResponse().setBody(asset));

        onView(withId(R.id.reportProblemText))
                .perform(typeText("The app crushes"));
        onView(withId(R.id.reportProblemText)).perform(closeSoftKeyboard());

        try {
             Thread.sleep(7500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.submit_report_btn)).perform(click());

        onView(withText("Report submitted successfully")).inRoot(RootMatchers
                .withDecorView(not(controlledActivity.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }


    /**
     * Test sending an empty report.
     */
    @Test
    public void testFailedReportSending() {
        onView(withId(R.id.submit_report_btn)).check(matches(isDisplayed()));

        onView(withId(R.id.reportProblemText))
                .perform(typeText(""));
        onView(withId(R.id.reportProblemText)).perform(closeSoftKeyboard());

        try {
            Thread.sleep(7500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.submit_report_btn)).perform(click());

        onView(withText("Cannot submit an empty report")).inRoot(RootMatchers
                .withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
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
