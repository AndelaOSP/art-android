package com.andela.art.sendfeedback;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.andela.art.R;
import com.andela.art.sendfeedback.presentation.SendFeedbackActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

/**
 * Test send feedback activity.
 */
@RunWith(AndroidJUnit4.class)
public class SendFeedbackActivityTest {

    @Rule
    public ActivityTestRule<SendFeedbackActivity> sendFeedbackActivityActivityTestRule =
            new ActivityTestRule<>(SendFeedbackActivity.class);

    /**
     * Test closing of the send feedback activity.
     */
    @Test
    public void testCloseOfSendFeedbackActivity() {
        onView(withId(R.id.close_sendfeedback)).check(matches(isDisplayed()));

        onView(withId(R.id.close_sendfeedback)).check(matches(isClickable()));

        onView(withId(R.id.close_sendfeedback)).perform(click());
    }

    /**
     * Test toolbar is displayed.
     */
    @Test
    public void testToolbarIsDisplayed() {
        onView(withText("Send Feedback")).check(matches(isDisplayed()));
    }

    /**
     * Test sending of feedback.
     */
    @Test
    public void testSendingFeedback() {
        onView(withId(R.id.submit_feedback)).check(matches(isDisplayed()));

        onView(withId(R.id.seedFeedbackText))
                .perform(typeText("I like the user interface."));
        onView(withId(R.id.seedFeedbackText)).perform(closeSoftKeyboard());

        onView(withId(R.id.submit_feedback)).check(matches(isDisplayed()));

    }
}
