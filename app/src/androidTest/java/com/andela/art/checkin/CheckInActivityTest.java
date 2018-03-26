package com.andela.art.checkin;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.andela.art.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Check in activity tests.
 */
public class CheckInActivityTest {
    /**
     * Setup required before each test.
     * @throws Exception - exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test rule.
     */
    @Rule
    public ActivityTestRule<CheckInActivity> mActivityRule = new ActivityTestRule<CheckInActivity>(
            CheckInActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent intent = new Intent(targetContext, CheckInActivity.class);
            intent.putExtra("name", "madge");
            intent.putExtra("email", "madge@mail.com");
            intent.putExtra("cohort", "18");
            return intent;
        }
    };

    /**
     * Teardown function after testing is done.
     * @throws Exception - exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test details fed from intent is displayed.
     * @throws Exception - exception
     */
    @Test
    public void testDisplayDetails() throws Exception {
        onView(withId(R.id.name)).check(matches(withText("MADGE")));
        onView(withId(R.id.email_text)).check(matches(withText("madge@mail.com")));
        onView(withId(R.id.cohort_number)).check(matches(withText("18")));
    }

    /**
     * Test that check in button is clickable.
     * @throws Exception - exception
     */
    @Test
    public void testClickCheckin() throws Exception {
        onView(withId(R.id.checkinButton)).check(matches(isClickable()));
    }

    /**
     * Test image is loaded into the image view.
     * @throws Exception - exception.
     */
    @Test
    public void testLoadResizedImage() throws Exception {
        onView(withId(R.id.ivPhoto)).check(matches(isDisplayed()));
    }

}
