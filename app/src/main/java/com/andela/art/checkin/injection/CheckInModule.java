package com.andela.art.checkin.injection;

import com.andela.art.api.ApiService;
import com.andela.art.checkin.CheckInPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Check In module.
 */
@Module
public class CheckInModule {


    /**
     * Provide check in presenter.
     * @param apiService - ApiService
     * @return CheckInPresenter
     */
    @PerActivity
    @Provides
    CheckInPresenter provideCheckInPresenter(ApiService apiService) {
        return new CheckInPresenter(apiService);
    }

}
