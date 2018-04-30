package com.andela.art.sendfeedback.presentation;

import com.andela.art.api.ApiService;
import com.andela.art.root.Presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Send Feedback Presenter.
 */
public class SendFeedbackPresenter implements Presenter<SendFeedbackView> {
    private final ApiService apiService;
    private Disposable disposable;
    private SendFeedbackView sendFeedbackView;

    /**
     * Send Feedback presenter constructor.
     *
     * @param apiService - send feedback service.
     */
    public SendFeedbackPresenter(ApiService apiService) {
        this.apiService = apiService;
    }

    /**
     * Send feedback.
     *
     * @param reportedBy - the user sending feedback
     * @param message - the description for the feedback
     * @param reportType - the type of report
     */
    public void sendFeedback(String reportedBy, String message, String reportType) {
        disposable = apiService.sendFeedback(reportedBy, message, reportType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sendFeedbackView::sendFeedbackSuccess,
                        sendFeedbackView::sendFeedbackError
                );
    }

    /**
     * Instantiate view that will be used by the presenter.
     * @param view view that will be instantiated
     */
    @Override
    public void attachView(SendFeedbackView view) {
        this.sendFeedbackView = view;
    }

    /**
     * Dispose disposable after activity stops.
     */
    public void dispose() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
