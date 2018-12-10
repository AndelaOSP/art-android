package com.andela.art.securitydashboard.injection;

import com.andela.art.api.ApiService;
import com.andela.art.securitydashboard.presentation.NfcPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kalela on 20/11/18.
 */

@Module
public class NfcEntryModule {
    /**
     * Provide nfc presenter instance.
     *
     * @param apiService api service to be used by nfc presenter
     *
     * @return nfc presenter instance
     */
    @Activity
    @Provides
    NfcPresenter provideNfcPresenter(ApiService apiService) {
        return new NfcPresenter(apiService);
    }
}
