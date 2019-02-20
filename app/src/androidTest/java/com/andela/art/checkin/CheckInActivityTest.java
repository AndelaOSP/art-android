package com.andela.art.checkin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;

import com.andela.art.R;
import com.andela.art.models.Asignee;
import com.andela.art.models.Asset;
import com.andela.art.securitydashboard.presentation.SecurityDashboardActivity;
import com.andela.art.utils.MockWebServerRule;
import com.andela.art.utils.RestServiceTestHelper;
import com.andela.art.utils.WaitActivityIsResumedIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import okhttp3.mockwebserver.MockResponse;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
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
            CheckInActivity.class, true, false) {
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
            asset.setAllocationStatus("Checkout");
            asset.setAssignedTo(asignee);
            asset.setCheckinStatus("checked_out");
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

    @Rule
    public MockWebServerRule mockWebServerRule = new MockWebServerRule();

    /**
     * Test details fed from intent is displayed.
     */
    @Test
    public void displayDetails() {
        mActivityRule.launchActivity(null);
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
        mActivityRule.launchActivity(null);
        onView(withId(R.id.ivPhoto)).check(matches(isDisplayed()));
    }

    /**
     * Test toolbar is displayed.
     */
    @Test
    public void testToolbarIsDisplayed() {
        mActivityRule.launchActivity(null);
        onView(withId(R.id.check_in_toolbar)).check(matches(isDisplayed()));
    }

    /**
     * Test check out functionality.
     * @throws Exception - exception
     */
    @Test
    public void testClickCheckOut() throws Exception {
        String fileName = "check-in-asset.json";
        String fileName2 = "asset_response.json";
        String checkSerial = RestServiceTestHelper.
                getStringFromFile(getTargetContext(), fileName2);
        String checkIn = RestServiceTestHelper.
                getStringFromFile(getTargetContext(), fileName);
        mockWebServerRule.server.enqueue(new MockResponse().setBody(checkIn));
        mockWebServerRule.server.enqueue(new MockResponse().setBody(checkSerial));
        mActivityRule.launchActivity(null);

        onView(withId(R.id.checkInButton)).perform(click());
        IdlingResource idlingResource = new WaitActivityIsResumedIdlingResource(
                SecurityDashboardActivity.class.getName());
        IdlingRegistry.getInstance().register(idlingResource);
        IdlingRegistry.getInstance().unregister(idlingResource);

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.addSerial)).perform(click());
        onView(withId(R.id.serial_edit_text)).
                perform(typeText("serial1"), closeSoftKeyboard());
        closeSoftKeyboard();
        onView(withId(R.id.submit)).perform(click());


        IdlingResource idlingResource2 = new WaitActivityIsResumedIdlingResource(
               CheckInActivity.class.getName());
        IdlingRegistry.getInstance().register(idlingResource2);
        IdlingRegistry.getInstance().unregister(idlingResource2);

        onView(withId(R.id.checkInButton)).check(matches(withText("CHECK-OUT")));
    }

    /**
     * Test check in functionality.
     */
    @Test
    public void testClickCheckIn() {
        mActivityRule.launchActivity(null);
        onView(withId(R.id.checkInButton)).check(matches(withText("CHECK-IN")));
    }

    /**
     * Teardown function after testing is done.
     */
    @After
    public void tearDown() {
    }
}
