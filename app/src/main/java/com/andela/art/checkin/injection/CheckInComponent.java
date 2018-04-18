package com.andela.art.checkin.injection;


import com.andela.art.api.ApiModule;
import com.andela.art.checkin.CheckInActivity;
import com.andela.art.root.ApplicationComponent;

import dagger.Component;

/**
 * Check in component.
 */
@PerActivity
@Component(dependencies = {ApplicationComponent.class},
        modules = {CheckInModule.class,
                ApiModule.class})
public interface CheckInComponent {
    /**
     * Inject check in activity.
     * @param checkInActivity activity to inject
     */
    void inject(CheckInActivity checkInActivity);
}
