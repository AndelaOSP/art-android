package com.andela.art.reportproblem;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.andela.art.R;
import com.andela.art.reportproblem.presentation.ReportProblemActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

/**
 * Test Report a Problem Activity.
 */
@RunWith(AndroidJUnit4.class)
public class ReportProblemTest {

    @Rule
    public IntentsTestRule<ReportProblemActivity> activityTestRule =
            new IntentsTestRule<>(ReportProblemActivity.class,  true, true);

    /**
     * Test closing of the report a problem activity.
     */
    @Test
    public void testCloseOfReportProblemActivity() {
        onView(withId(R.id.close_report_button)).check(matches(isDisplayed()));

        onView(withId(R.id.close_report_button)).check(matches(isClickable()));
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
     */
    @Test
    public void testReportSending() {
        onView(withId(R.id.submit_report_btn)).check(matches(isDisplayed()));

        onView(withId(R.id.reportProblemText))
                .perform(typeText("The app crushes"));
        closeSoftKeyboard();

        onView(withId(R.id.submit_report_btn)).perform(click());
    }
}
