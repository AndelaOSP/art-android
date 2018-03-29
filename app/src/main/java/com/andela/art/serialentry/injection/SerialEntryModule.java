package com.andela.art.serialentry.injection;

import com.andela.art.api.ApiService;
import com.andela.art.serialentry.presentation.SerialPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zack on 3/5/18.
 */

@Module
public class SerialEntryModule {

    /**
     * Provide serial presenter instance.
     *
     * @param apiService api service to be used by serial presenter
     *
     * @return serial presenter instance
     */
    @Activity
    @Provides
    SerialPresenter provideSerialPresenter(ApiService apiService) {
        return new SerialPresenter(apiService);
    }
}
