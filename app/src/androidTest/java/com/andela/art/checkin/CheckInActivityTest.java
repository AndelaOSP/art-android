package com.andela.art.checkin;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.andela.art.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CheckInActivityTest {

    @Rule
    public ActivityTestRule<CheckInActivity> mActivityRule = new ActivityTestRule<CheckInActivity>(
            CheckInActivity.class)
    {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent intent = new Intent(targetContext, CheckInActivity.class);
            intent.putExtra("name", "madge");
            intent.putExtra("email", "madge.mail.com");
            return intent;
        }
    };

    @Before
    public void setUp(){
    }

    @Test
    public void testImageViewIsDisplayed()
    {
        onView(withId(R.id.ivPhoto)).check(matches(isDisplayed()));
    }

    @Test
    public void testScannedUserEmailIsDisplayed()
    {
        onView(withId(R.id.email_text)).check(matches(isDisplayed()));
    }

    @Test
    public void testDataSentViaIntentIsDisplayed()
    {
        onView(withId(R.id.name)).check(matches(withText("madge")));
        onView(withId(R.id.email_text)).check(matches(withText("madge.mail.com")));
    }

    @Test
    public void testScannedCohortIsDisplayed()
    {
        onView(withId(R.id.cohort_number)).check(matches(isDisplayed()));
    }
}