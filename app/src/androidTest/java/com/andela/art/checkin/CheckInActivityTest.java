package com.andela.art.checkin;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.andela.art.R;

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
        String name = "madge";
        String email = "madge.mail.com";
        onView(withId(R.id.name)).check(matches(withText(name)));
        onView(withId(R.id.email_text)).check(matches(withText(email)));
    }

    @Test
    public void testScannedCohortIsDisplayed()
    {
        onView(withId(R.id.cohort_number)).check(matches(isDisplayed()));
    }
}