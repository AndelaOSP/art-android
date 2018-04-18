package com.andela.art.checkin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.andela.art.R;
import com.andela.art.models.Asignee;
import com.andela.art.models.Asset;

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
     */
    @Before
    public void setUp() {
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

            Asignee asignee = new Asignee();
            asignee.setCohort(18);
            asignee.setDateJoined("20-12-2017");
            asignee.setEmail("mudge.fudge@mail.com");
            asignee.setFirstName("mudge");
            asignee.setLastName("Fudge");
            asignee.setFullName("Mudge Fudge");
            asignee.setPicture("R.drawable.photo");
            asignee.setId(1);
            asignee.setSlackHandle("");
            asignee.setLastLogin("");
            asignee.setLastModified("");


            Asset asset = new Asset();
            asset.setId(1);
            asset.setAllocationStatus("Checkin");
            asset.setAssignedTo(asignee);
            asset.setCheckinStatus("Checkin");
            asset.setCreatedAt("12-09-2018");
            asset.setItemCode("ER34521");
            asset.setLastModified("");
            asset.setModelNumber("");
            asset.setSerialNumber("CRT5647Y");

            Bundle bundle = new Bundle();
            bundle.putSerializable("asset", asset);
            intent.putExtras(bundle);

            return intent;
        }
    };

    /**
     * Test details fed from intent is displayed.
     */
    @Test
    public void displayDetails() {
        onView(withId(R.id.name)).check(matches(withText("MUDGE FUDGE")));
        onView(withId(R.id.email_text)).check(matches(withText("mudge.fudge@mail.com")));
        onView(withId(R.id.cohort_number)).check(matches(withText("18")));
        onView(withId(R.id.serial_info)).check(matches(withText("CRT5647Y")));
    }

    /**
     * Test image is loaded into the image view.
     */
    @Test
    public void loadResizedImage() {
        onView(withId(R.id.ivPhoto)).check(matches(isDisplayed()));
    }

    /**
     * Test that check in button is clickable.
     */
    @Test
    public void testClickCheckin() {
        onView(withId(R.id.checkInButton)).check(matches(isClickable()));
    }

    /**
     * Test toolbar is displayed.
     */
    @Test
    public void testToolbarIsDisplayed() {
        onView(withId(R.id.check_in_toolbar)).check(matches(isDisplayed()));
    }

    /**
     * Teardown function after testing is done.
     */
    @After
    public void tearDown() {
    }
}
