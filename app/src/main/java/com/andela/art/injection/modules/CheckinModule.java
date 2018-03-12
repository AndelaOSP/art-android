package com.andela.art.injection.modules;

import com.andela.art.checkin.CheckInView;
import com.andela.art.checkin.data.CheckInService;
import com.andela.art.injection.scope.PerActivity;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@PerActivity
@Module
public class CheckinModule {
    private CheckInView mView;

    public CheckinModule(CheckInView view) {
        mView = view;
    }

    @PerActivity
    @Provides
    CheckInService provideCheckInService(Retrofit retrofit) {
        return retrofit.create(CheckInService.class);
    }

    @PerActivity
    @Provides
    CheckInView provideContext() {
        return mView;
    }
}
