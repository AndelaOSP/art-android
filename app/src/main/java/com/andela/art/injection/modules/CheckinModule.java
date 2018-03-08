package com.andela.art.injection.modules;

import com.andela.art.data.CheckInService;
import com.andela.art.injection.scope.PerActivity;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@PerActivity
@Module
public class CheckinModule {

    @PerActivity
    @Provides
    CheckInService provideCheckInService(Retrofit retrofit) {
        return retrofit.create(CheckInService.class);
    }
}
