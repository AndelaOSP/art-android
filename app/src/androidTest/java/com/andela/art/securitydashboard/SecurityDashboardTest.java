package com.andela.art.securitydashboard;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import com.andela.art.R;
import com.andela.art.settings.SettingsActivity;

import org.hamcrest.Matcher;
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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Tests for the security dashboard.
 */
@RunWith(AndroidJUnit4.class)
public class SecurityDashboardTest {

    @Rule
    public ActivityTestRule<SecurityDashboardActivity> securityDashboardActivityTestRule =
            new ActivityTestRule<>(SecurityDashboardActivity.class);

    /**
     * Tests that the toolbar is shown on the security dashboard.
     */
    @Test
    public void toolBarIsRenderedInDashboardActivity() {
        onView(withId(R.id.mToolBar)).check(matches(isDisplayed()));
    }

    /**
     * Tests that the menu is shown on the security dashboard in the toolbar by checking the
     * presence of the settings menu item.
     */
    @Test
    public void menuIsRenderedInDashboardActivity() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.settings)).check(matches(isDisplayed()));
    }

    /**
     * Tests that the settings activity is opened when a user clicks the settings menu item from
     * the security dashboard.
     */
    @Test
    public void clickingSettingsMenuItem__OpensSettingsActivity() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        Intents.init();

        onView(withText(R.string.settings)).perform(click());

        intended(hasComponent(SettingsActivity.class.getName()));
        Intents.release();
    }

    /**
     * Test the dashboard activity that it loads and displays all the views.
     */
    @Test
    public void dashboardViewsAreRenderedInDashboardActivity() {
        onView(withId(R.id.profile_picture)).check(matches(isDisplayed()));

        onView(withId(R.id.full_name)).perform(setTextInTextView("Full name"));
        onView(withId(R.id.full_name)).check(matches(isDisplayed()));
        onView(withId(R.id.full_name)).check(matches(withText("Full name")));

        onView(withId(R.id.email_address)).perform(setTextInTextView("test_email@domain.com"));
        onView(withId(R.id.email_address)).check(matches(isDisplayed()));
        onView(withId(R.id.email_address)).check(matches(withText("test_email@domain.com")));

        onView(withId(R.id.check_serial)).check(matches(isDisplayed()));
        onView(withId(R.id.check_serial)).check(matches(withText(R.string.check_serial)));
    }

    /**
     * Custom Matchers method to help set the text of a TextView while testing using Espresso.
     *
     * @param value The string to set on the TextView
     * @return ViewAction
     */
    public static ViewAction setTextInTextView(final String value) {
        return new ViewAction() {
            @SuppressWarnings("unchecked")
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isAssignableFrom(TextView.class));
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((TextView) view).setText(value);
            }

            @Override
            public String getDescription() {
                return "replace text";
            }
        };
    }
}
