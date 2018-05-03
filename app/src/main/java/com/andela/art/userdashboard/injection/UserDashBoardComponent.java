package com.andela.art.userdashboard.injection;

import com.andela.art.api.ApiModule;
import com.andela.art.root.ApplicationModule;
import com.andela.art.userdashboard.presentation.UserDashBoardActivity;

import dagger.Component;

/**
 * Created by lewismbogo on 02/05/2018.
 */
@Component(modules = {UserDashBoardModule.class,
ApplicationModule.class,
ApiModule.class})
public interface UserDashBoardComponent {

    /**
     * Inject check in activity.
     * @param activity activity to inject
     */
    void inject(UserDashBoardActivity activity);
}
