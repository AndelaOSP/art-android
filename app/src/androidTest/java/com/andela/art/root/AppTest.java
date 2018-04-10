package com.andela.art.root;

import android.content.res.Configuration;
import android.os.RemoteException;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import com.andela.art.login.LoginActivity;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;

/**
 * Created by Jeffkungu on 14/03/2018.
 */
@Ignore
@RunWith(AndroidJUnit4.class)
public class AppTest {

    UiDevice device = UiDevice.getInstance(getInstrumentation());

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule =
            new ActivityTestRule<>(LoginActivity.class, true, false);

    /**
     * Tests that an activity launches in portrait even if the device is in landscape mode.
     *
     * @throws RemoteException if an error occurs.
     */
    @Test
    public void deviceInLandscape_ActivityLaunchesInPortrait() throws RemoteException {
        device.setOrientationLeft();

        loginActivityRule.launchActivity(null);

        assertEquals(Configuration.ORIENTATION_PORTRAIT, getActivityOrientation());
    }

    /**
     * Tests that an activity remains in portrait even if the device's orientation is altered
     * to landscape mode.
     *
     * @throws RemoteException if an error occurs.
     */
    @Test
    public void changeDeviceToLandscape_OrientationRemainsPortrait() throws RemoteException {
        loginActivityRule.launchActivity(null);

        device.setOrientationLeft();

        Assert.assertEquals(Configuration.ORIENTATION_PORTRAIT, getActivityOrientation());
    }

    /**
     * Get activity orientation.
     *
     * @return orientation - return the orientation of the activity under test.
     */
    private int getActivityOrientation() {
        return loginActivityRule.getActivity().getResources().getConfiguration().orientation;
    }
}
