package com.andela.art.sendfeedback;

import android.app.Activity;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.widget.Toast;

import com.andela.art.R;
import com.andela.art.sendfeedback.presentation.SendFeedbackActivity;
import com.andela.art.settings.SettingsActivity;
import com.andela.art.utils.ControlledActivityTestRule;
import com.andela.art.utils.MockWebServerRule;
import com.andela.art.utils.OkHttpIdlingResourceRule;
import com.andela.art.utils.RestServiceTestHelper;

import org.junit.After;
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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.not;

/**
 * Test send feedback activity.
 */
@RunWith(AndroidJUnit4.class)
public class SendFeedbackActivityTest {

    @Rule
    public ActivityTestRule<SendFeedbackActivity> sendFeedbackActivityActivityTestRule =
            new ActivityTestRule<>(SendFeedbackActivity.class, true, false);

    @Rule
    public ControlledActivityTestRule controlledActivity =
            new ControlledActivityTestRule(SettingsActivity.class);

    @Rule
    public OkHttpIdlingResourceRule okHttpIdlingResource = new OkHttpIdlingResourceRule();

    @Rule
    public MockWebServerRule mockWebServerRule = new MockWebServerRule();

    /**
     * Test closing of the send feedback activity.
     */
    @Test
    public void testCloseOfSendFeedbackActivity() {
        sendFeedbackActivityActivityTestRule.launchActivity(null);
        onView(withId(R.id.close_sendfeedback)).check(matches(isDisplayed()));
        onView(withId(R.id.close_sendfeedback)).perform(click());

        assertTrue(sendFeedbackActivityActivityTestRule.getActivity().isFinishing());
    }

    /**
     * Test sending of feedback.
     * @throws Exception - exception
     */
    @Test
    public void testSendingFeedback() throws Exception {
        String fileName = "feedback_successful.json";
        String asset = RestServiceTestHelper.
                getStringFromFile(getTargetContext(), fileName);

        mockWebServerRule.server.enqueue(new MockResponse().setBody(asset));
        sendFeedbackActivityActivityTestRule.launchActivity(null);

        onView(withId(R.id.seedFeedbackText))
                .perform(typeText("I like the user interface."));
        onView(withId(R.id.seedFeedbackText)).perform(closeSoftKeyboard());

        Thread.sleep(7500);

        onView(withId(R.id.submit_feedback)).perform(click());

        onView(withText("Feedback sent successfully")).inRoot(RootMatchers
                .withDecorView(not(sendFeedbackActivityActivityTestRule.
                        getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

    }

    /**
     * Test sending of empty feedback fails.
     * @throws InterruptedException if an error occurs.
     */
    @Test
    public void testSendingEmptyFeedback() throws InterruptedException {
        sendFeedbackActivityActivityTestRule.launchActivity(null);

        onView(withId(R.id.seedFeedbackText))
                .perform(typeText(""));
        onView(withId(R.id.seedFeedbackText)).perform(closeSoftKeyboard());

        Thread.sleep(7500);
        onView(withId(R.id.submit_feedback)).perform(click());
        onView(withText("Please provide feedback")).inRoot(RootMatchers
                .withDecorView(not(sendFeedbackActivityActivityTestRule.
                        getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

    }

    /**
     * Gets the current activity from the activity ActivityLifecycleMonitorRegistry.
     * @return currentActivity.
     */
    public Activity getCurrentActivity() {
        final Activity[] currentActivity = new Activity[1];
        getInstrumentation().runOnMainSync(() -> {
            Collection<Activity> resumedActivities = ActivityLifecycleMonitorRegistry
                    .getInstance().getActivitiesInStage(Stage.RESUMED);
            for (Activity act : resumedActivities) {
                currentActivity[0] = act;
                break;
            }
        });

        return currentActivity[0];
    }

    /**
     * Remove any toast message that is still shown.
     */
    @After
    public void tearDown() {
        Toast toast = sendFeedbackActivityActivityTestRule.getActivity().toast;
        if (toast != null) {
            toast.cancel();
        }
    }
}
