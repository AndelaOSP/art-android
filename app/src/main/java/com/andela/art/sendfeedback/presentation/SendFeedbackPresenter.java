package com.andela.art.sendfeedback.presentation;

import com.andela.art.api.ApiService;

/**
 * Send Feedback Presenter.
 */
public class SendFeedbackPresenter {
    private final ApiService apiService;

    /**
     * Send Feedback presenter constructor.
     *
     * @param apiService - send feedback service.
     */
    public SendFeedbackPresenter(ApiService apiService) {
        this.apiService = apiService;
    }
}
