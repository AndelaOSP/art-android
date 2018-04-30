package com.andela.art.sendfeedback.injection;

import com.andela.art.api.ApiService;
import com.andela.art.sendfeedback.presentation.SendFeedbackPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Send Feedback Module.
 */
@Module
public class SendFeedbackModule {
    /**
     * Provide send feedback presenter instance.
     *
     * @param apiService api service to be used dby the send feedback presenter.
     *
     * @return SendFeedbackModule.
     */
    @Provides
    SendFeedbackPresenter provideSendFeedbackPresenter(ApiService apiService) {
        return new SendFeedbackPresenter(apiService);
    }

}
