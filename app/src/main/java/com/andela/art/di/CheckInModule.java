package com.andela.art.di;

import com.andela.art.api.CheckInService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Check In module.
 */
@Module
public class CheckInModule {

    /**
     * provide check in service.
     * @param retrofit - retrofit
     * @return CheckInService
     */
    @Provides
    CheckInService provideCheckInService(Retrofit retrofit) {
        return retrofit.create(CheckInService.class);
    }

}
