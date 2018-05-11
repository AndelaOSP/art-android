package com.andela.art.userdashboard.injection;

import com.andela.art.api.ApiModule;
import com.andela.art.root.ApplicationComponent;
import com.andela.art.root.ApplicationModule;
import com.andela.art.securitydashboard.injection.Activity;
import com.andela.art.userdashboard.presentation.AssetSliderFragment;
import com.andela.art.userdashboard.presentation.UserDashBoardActivity;
import com.andela.art.userdashboard.presentation.UserDashBoardFragment;

import dagger.Component;

/**
 * Created by lewismbogo on 02/05/2018.
 */
@Activity
@Component(dependencies = ApplicationComponent.class,
        modules = {ApplicationModule.class,
                    ApiModule.class,
                UserDashBoardModule.class})
public interface UserDashBoardComponent {

    /**
     * Inject check in activity.
     * @param activity activity to inject
     */
    void inject(UserDashBoardActivity activity);
}
