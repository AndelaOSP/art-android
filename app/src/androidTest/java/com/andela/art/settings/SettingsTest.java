package com.andela.art.settings;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.andela.art.R;
import com.andela.art.securitydashboard.SecurityDashboardActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Tests for the settings activity.
 */
@RunWith(AndroidJUnit4.class)
public class SettingsTest {

    @Rule
    public ActivityTestRule<SecurityDashboardActivity> securityDashboardActivityTestRule =
            new ActivityTestRule<>(SecurityDashboardActivity.class);

    /**
     * Tests that clicking the tool bar home button on the settings activity returns user to
     * the security dashboard activity.
     */
    @Test
    public void clickingHomeButton__ReturnsToSecurityDashboardActivity() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        Intents.init();
        onView(withText(R.string.settings)).perform(click());
        intended(hasComponent(SettingsActivity.class.getName()));
        Intents.release();

        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());

        onView(withText(R.string.app_title)).check(matches(isDisplayed()));
    }
}
