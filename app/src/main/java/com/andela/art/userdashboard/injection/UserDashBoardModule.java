package com.andela.art.userdashboard.injection;

import com.andela.art.api.ApiService;
import com.andela.art.securitydashboard.injection.Activity;
import com.andela.art.userdashboard.presentation.AssetsPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lewismbogo on 02/05/2018.
 */
@Module
public class UserDashBoardModule {

    /**
     * Provide asset presenter instance.
     *
     * @param apiService api service to be used by asset presenter
     *
     * @return asset presenter instance
     */
    @Activity
    @Provides
    AssetsPresenter provideSerialPresenter(ApiService apiService) {
        return new AssetsPresenter(apiService);
    }
}
