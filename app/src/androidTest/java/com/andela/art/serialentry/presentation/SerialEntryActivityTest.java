package com.andela.art.serialentry.presentation;

import android.support.test.espresso.intent.matcher.BundleMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.andela.art.R;
import com.andela.art.checkin.CheckInActivity;
import com.andela.art.serialentry.data.Asset;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;


/**
 * Created by zack on 3/20/18.
 */
public class SerialEntryActivityTest {

    /**
     * {@link IntentsTestRule} is a JUnit {@link Rule @Rule} to launch your activity under test.
     *
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */

    @Rule
    public IntentsTestRule<SerialEntryActivity> activityTestRule =
            new IntentsTestRule<>(SerialEntryActivity.class,  true,  false);


    @Rule
    public OkHttpIdlingResourceRule okHttpIdlingResource = new OkHttpIdlingResourceRule();

    @Rule
    public MockWebServerRule mockWebServerRule = new MockWebServerRule();


    /**
     * Test set up method.
     * @throws Exception if an error occurs
     */
    @Before
    public void setUp() throws Exception {
        Asset asset = new Asset();
        asset.setId(1);
        asset.setUserId(1);
        asset.setItemCode("1234");
        asset.setSerialNumber("1234");

    }

    /**
     * Test dialog box.
     * @throws IOException if an error occurs
     */
    @Test
    public void testDialogBoxAppearsWhenButtonClicked() throws IOException {
        activityTestRule.launchActivity(null);
        onView(withId(R.id.addSerial))
                .perform(click());
        onView(withText(R.string.enter_text)).check(matches(isDisplayed()));
    }

    /**
     * Test asset retrieved and intent sent.
     * @throws IOException if an error occurs
     */
    @Test
    public void testAssetDataIsRetrievedWhenCorrectSerialIsEntered() throws IOException {
        String json = "{\"id\": 1,\"userId\": 1,\"item_code\": \"123\",\"serial_number\":\"123\"}";
        mockWebServerRule.server.enqueue(new MockResponse().
                setBody(json));
        activityTestRule.launchActivity(null);
        onView(withId(R.id.email_address)).check(matches(isDisplayed()));

        onView(withId(R.id.addSerial)).
                perform(click());
        onView(withId(R.id.serial_edit_text)).
                perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.submit)).perform(click());
        intended(allOf(hasExtras(BundleMatchers.hasKey("asset")),
                hasComponent(CheckInActivity.class.getName())));

    }
}
