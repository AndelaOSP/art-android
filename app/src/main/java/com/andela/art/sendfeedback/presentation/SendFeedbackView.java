package com.andela.art.sendfeedback.presentation;

import com.andela.art.models.SendFeedback;
import com.andela.art.root.View;

/**
 * Send feedback view.
 */
public interface SendFeedbackView extends View {
    /**
     * successful submission of feedback.
     *
     * @param sendFeedback sendFeedback model
     */
    void sendFeedbackSuccess(SendFeedback sendFeedback);

    /**
     * Unsuccessful submission of feedback.
     *
     * @param e error
     */
    void sendFeedbackError(Throwable e);
}
