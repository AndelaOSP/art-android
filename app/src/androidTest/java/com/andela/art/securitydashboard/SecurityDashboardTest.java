package com.andela.art.securitydashboard;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.andela.art.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

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
}
