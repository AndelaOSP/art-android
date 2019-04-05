package com.andela.art.securitydashboard;

import android.os.Build;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.matcher.BundleMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Toast;

import com.andela.art.R;
import com.andela.art.checkin.CheckInActivity;
import com.andela.art.securitydashboard.presentation.SecurityDashboardActivity;
import com.andela.art.utils.ConditionalIgnoreRule;
import com.andela.art.utils.MockWebServerRule;
import com.andela.art.utils.OkHttpIdlingResourceRule;
import com.andela.art.utils.RestServiceTestHelper;
import com.andela.art.utils.WaitActivityIsResumedIdlingResource;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.times;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.not;


/**
 * Created by zack on 3/20/18.
 */
@RunWith(AndroidJUnit4.class)
public class SerialSecurityDashboardActivityTest {



    /**
     * {@link IntentsTestRule} is a JUnit {@link Rule @Rule} to launch your activity under test.
     *
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    public IntentsTestRule<SecurityDashboardActivity> activityTestRule =
            new IntentsTestRule<>(SecurityDashboardActivity.class,
                    true, false);

    @Rule
    public OkHttpIdlingResourceRule okHttpIdlingResource = new OkHttpIdlingResourceRule();

    @Rule
    public MockWebServerRule mockWebServerRule = new MockWebServerRule();

    @Rule
    public ConditionalIgnoreRule rule = new ConditionalIgnoreRule();

    /**
     * Test dialog box.
     * @throws IOException if an error occurs
     */
    @Test
    @ConditionalIgnoreRule.ConditionalIgnore(condition = RunningOnDevice.class)
    public void testDialogBoxAppearsWhenButtonClicked() throws IOException {
        activityTestRule.launchActivity(null);
        onView(withId(R.id.addSerial))
                .perform(click());
        onView(withText(R.string.enter_text)).check(matches(isDisplayed()));
    }

    /**
     * Test asset retrieved and intent sent.
     * @throws Exception if an error occurs
     */
    @Test
    @ConditionalIgnoreRule.ConditionalIgnore(condition = RunningOnDevice.class)
    public void testAssetDataIsRetrievedWhenCorrectSerialIsEntered() throws Exception {
        String fileName = "asset_response.json";
        String asset = RestServiceTestHelper.
                getStringFromFile(getTargetContext(), fileName);
        mockWebServerRule.server.enqueue(new MockResponse().setBody(asset));
        activityTestRule.launchActivity(null);
        onView(withId(R.id.addSerial)).perform(click());
        onView(withId(R.id.serial_edit_text)).
                perform(typeText("123"), closeSoftKeyboard());
        closeSoftKeyboard();
        onView(withId(R.id.submit)).perform(click());
        IdlingResource idlingResource = new WaitActivityIsResumedIdlingResource(
                CheckInActivity.class.getName());
        IdlingRegistry.getInstance().register(idlingResource);
        intended(allOf(hasExtras(BundleMatchers.hasKey("asset")),
                hasComponent(CheckInActivity.class.getName())), times(1));
        IdlingRegistry.getInstance().unregister(idlingResource);

    }

    /**
     * Test an Unassigned asset displays a toast.
     * @throws Exception if an error occurs
     */
    @Test
    @ConditionalIgnoreRule.ConditionalIgnore(condition = RunningOnDevice.class)
    public void testUnassignedAssetDisplaysUnassignedToast() throws Exception {
        String fileName = "null_asset_response.json";
        String nullAsset = RestServiceTestHelper.
                getStringFromFile(getTargetContext(), fileName);
        mockWebServerRule.server.enqueue(new MockResponse().setBody(nullAsset));
        activityTestRule.launchActivity(null);
        onView(withId(R.id.addSerial)).perform(click());
        onView(withId(R.id.serial_edit_text)).
                perform(typeText("123"), closeSoftKeyboard());
        closeSoftKeyboard();

        onView(withId(R.id.submit)).perform(click());

        onView(withText("Asset not assigned.")).inRoot(RootMatchers
                .withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

    }

    /**
     * Test email is displayed.
     * @throws IOException if an error occurs
     */
    @Test
    public void testEmailIsDisplayed() throws IOException {
        activityTestRule.launchActivity(null);
        onView(withId(R.id.email_address))
                .check(matches(allOf(isDisplayed(), withText("zac@gmail.com"))));
    }

    /**
     * Test display name is displayed.
     * @throws IOException if an error occurs
     */
    @Test
    public void testDisplayNameIsDisplayed() throws IOException {
        activityTestRule.launchActivity(null);
        onView(withId(R.id.display_name))
                .check(matches(allOf(isDisplayed(),
                        withText("Zacharia Mwangi"))
                ));
    }

    /**
     * Test pressing the back button twice within 2 seconds exits the app.
     */
    @Test
    public void backButtonToExitPressedTwiceExitsTheApp() {
        activityTestRule.launchActivity(null);
        pressBack();

        // Added a sleep statement to match the app's execution delay.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();
    }

    /**
     * Remove any toast message that is still shown.
     */
    @After
    public void tearDown() {
        Toast toast = activityTestRule.getActivity().toast;
        if (toast != null) {
            toast.cancel();
        }
    }

    /**
     * Condition that should cause a test to be ignored.
     */
    public class RunningOnDevice implements ConditionalIgnoreRule.IgnoreCondition {
        /**
         * Return boolean if condition is satisfied or not.
         * @return boolean
         */
        public boolean isSatisfied() {
            return !Build.PRODUCT.startsWith("sdk_google");
        }
    }
}
