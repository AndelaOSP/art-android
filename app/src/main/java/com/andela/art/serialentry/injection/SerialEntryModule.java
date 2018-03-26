package com.andela.art.serialentry.injection;

import com.andela.art.api.ApiService;
import com.andela.art.common.Activity;
import com.andela.art.serialentry.presentation.SerialPresenter;
import com.andela.art.serialentry.presentation.SerialView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zack on 3/5/18.
 */

@Module
public class SerialEntryModule {


    @Activity
    @Provides
    SerialPresenter provideSerialPresenter(ApiService apiService){
        return new SerialPresenter(apiService);
    }
}
