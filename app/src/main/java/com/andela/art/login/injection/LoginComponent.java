package com.andela.art.login.injection;

import com.andela.art.root.Activity;
import com.andela.art.root.ApplicationComponent;
import com.andela.art.root.ApplicationModule;
import com.andela.art.login.LoginActivity;
import com.andela.art.settings.SettingsActivity;

import dagger.Component;

/**
 * Created by zack on 4/11/18.
 */
@Activity
@Component(dependencies = {ApplicationComponent.class},
            modules = { ApplicationModule.class, LoginModule.class})
public interface LoginComponent {
    /**
     * Inject the login activity.
     *
     * @param loginActivity - The target activity
     */
    void inject(LoginActivity loginActivity);

    /**
     * Inject the settings activity.
     *
     * @param settingsActivity - SettingsActivity
     */
    void inject(SettingsActivity settingsActivity);
}
