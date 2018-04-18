package com.andela.art.securitydashboard.injection;

import com.andela.art.api.ApiModule;
import com.andela.art.root.ApplicationComponent;
import com.andela.art.firebase.FirebaseModule;
import com.andela.art.root.ApplicationModule;
import com.andela.art.securitydashboard.presentation.SecurityDashboardActivity;

import dagger.Component;

/**
 * Created by zack on 3/5/18.
 */
@Activity
@Component(dependencies = {ApplicationComponent.class},
        modules = {
                ApplicationModule.class,
                ApiModule.class,
                SerialEntryModule.class,
                FirebaseModule.class,
                FirebasePresenterModule.class})
public interface SerialEntryComponent {
    /**
     * Inject serial presenter to serial entry activity.
     * @param securityDashboardActivity activity where serial presenter will be injected
     */
    void inject(SecurityDashboardActivity securityDashboardActivity);
}
