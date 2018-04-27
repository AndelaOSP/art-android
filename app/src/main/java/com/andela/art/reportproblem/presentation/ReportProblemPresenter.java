package com.andela.art.reportproblem.presentation;

import com.andela.art.api.ApiService;
import com.andela.art.root.Presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chadwalt on 4/17/18.
 */

public class ReportProblemPresenter implements Presenter<ReportProblemView> {
    private final ApiService apiService;
    private ReportProblemView reportProblemView;
    private Disposable disposable;

    /**
     * Report Problem constructor.
     *
     * @param apiService api service interface.
     */
    public ReportProblemPresenter(ApiService apiService) {
        this.apiService = apiService;
    }

    /**
     * Submit the report.
     *
     * @param reportedBy - the user submitting the problem
     * @param message - the description about the problem
     * @param reportType - the type of report
     *
     */
    public void reportProblem(String reportedBy, String message, String reportType) {
        disposable = apiService.reportProblem(reportedBy, message, reportType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        reportProblemView::reportProblemSuccess,
                        reportProblemView::reportProblemError
                );
    }

    /**
     * Instantiate view that will be used by the presenter.
     * @param view view that will be instantiated
     */
    @Override
    public void attachView(ReportProblemView view) {
        this.reportProblemView = view;
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
