package com.andela.art.settings;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.andela.art.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Tests for the ReportProblem activity.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ReportProblemTest {

    @Rule
    public ActivityTestRule<ReportProblemActivity> mActivityRule =
            new ActivityTestRule<ReportProblemActivity>(ReportProblemActivity.class);

    /**
     * Test the rendering of the report a problem views.
     */
    @Test
    public void renderReportProblewViews() {
        onView(withText("Report a Problem")).check(matches(isDisplayed()));
        onView(withText("SOMETHING IS'NT WORKING?")).check(matches(isDisplayed()));

        onView(withId(R.id.reportProblem)).check(matches(isDisplayed()));
        onView(withId(R.id.close_report_button)).check(matches(isDisplayed()));
        onView(withId(R.id.submit_report_btn)).check(matches(isDisplayed()));
    }

    /**
     * Test the close button.
     */
    @Test
    public void clickCloseReportButton() {
        onView(withId(R.id.close_report_button)).perform(click());
    }
}
