package com.andela.art.serialentry.presentation;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Bundle;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.BundleMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;

import com.andela.art.R;
import com.andela.art.checkin.CheckInActivity;
import com.andela.art.serialentry.data.Asset;
import com.andela.art.util.Constants;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasPackage;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by zack on 3/20/18.
 */
public class SerialEntryActivityTest {

    @Rule
    public IntentsTestRule<SerialEntryActivity> activityTestRule =
            new IntentsTestRule<>(SerialEntryActivity.class,true,false);

    @Rule
    public OkHttpIdlingResourceRule okHttpIdlingResource = new OkHttpIdlingResourceRule();

    public  MockWebServer server = new MockWebServer();

    @Before
    public void setUp() throws Exception{
        server.start();
        Constants.BASE_URL = server.url("/").toString();
    }

    @Test
    public void testDialogBoxAppearsWhenButtonClicked() throws IOException{
        activityTestRule.launchActivity(null);
        onView(withId(R.id.addSerial))
                .perform(click());
        onView(withText(R.string.enter_text)).check(matches(isDisplayed()));
    }

    @Test
    public void testAssetDataIsRetrievedWhenCorrectSerialIsEntered() throws IOException{
       server.enqueue(new MockResponse().
                setBody("{\"id\": 1,\"userId\": 1,\"item_code\": \"1234\",\"serial_number\":\"1234\"}"));
        activityTestRule.launchActivity(null);
        Asset asset = new Asset();
        asset.setId(1);
        asset.setUserId(1);
        asset.setItemCode("1234");
        asset.setSerialNumber("1234");
        onView(withId(R.id.addSerial)).
                perform(click());
        onView(withHint(R.string.serial_hint)).
                perform(typeText("1234"));
        onView(withText("OK")).perform(click());
        closeSoftKeyboard();
        intended(allOf(hasExtras(BundleMatchers.hasKey("asset")),
                       hasComponent(CheckInActivity.class.getName())));

    }

    @After
    public void tearDown() throws Exception{
        server.shutdown();
    }

}