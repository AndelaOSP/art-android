package com.andela.art.incidentreport;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Toast;

import com.andela.art.R;
import com.andela.art.incidentreport.presentation.IncidentReportActivity;
import com.andela.art.models.Asset;
import com.andela.art.utils.MockWebServerRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import okhttp3.mockwebserver.MockResponse;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * Test Incident Report Activity.
 */
@RunWith(AndroidJUnit4.class)
public class IncidentReportTest {


    /**
     * Test rule.
     */
    @Rule
    public IntentsTestRule<IncidentReportActivity> incidentReportTestRule =
            new IntentsTestRule<>(IncidentReportActivity.class, true, false);

    /**
     * MockWebServer rule.
     */
    @Rule
    public MockWebServerRule mockWebServerRule = new MockWebServerRule();

    private Intent intent;

    /**
     * Setup required before each test.
     */
    @Before
    public void setUp() {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        intent = new Intent(targetContext, IncidentReportActivity.class);

        Asset asset = new Asset();
        Bundle bundle = new Bundle();
        bundle.putSerializable("asset", asset);

        intent.putExtras(bundle);
    }

    /**
     * Test incidence Report is Submitted successfully.
     */
    @Test
    public void testIncidentReportIsSubmittedSuccessfully() {
        mockWebServerRule.server.enqueue(new MockResponse().setResponseCode(201).setBody("{}"));
        incidentReportTestRule.launchActivity(intent);

        onView(withId(R.id.location)).perform(typeText("Nairobi"), closeSoftKeyboard());
        onView(withId(R.id.description)).perform(typeText("Babababbabahsa adjsa"));
        onView(withId(R.id.injuries)).perform(scrollTo(), typeText("None"));
        onView(withId(R.id.property)).perform(scrollTo(), typeText("Laptop"));
        onView(withId(R.id.witness)).perform(scrollTo(), typeText("Kimotho"));
        onView(withId(R.id.abstract_report)).perform(scrollTo(), typeText("Yes"),
                closeSoftKeyboard());
        onView(withId(R.id.incident_submit)).perform(scrollTo(), click());
        onView(withText("Incident reported successfully")).inRoot(RootMatchers
                .withDecorView(not(incidentReportTestRule.
                        getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

    }
    /**
     * Remove any toast message that is still shown.
     */
    @After
    public void tearDown() {
        Toast toast = incidentReportTestRule.getActivity().toast;
        if (toast != null) {
            toast.cancel();
        }
    }
}
