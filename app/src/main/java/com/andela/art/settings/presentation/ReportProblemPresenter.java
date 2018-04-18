package com.andela.art.settings.presentation;

import android.util.Log;

import com.andela.art.api.ApiService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chadwalt on 4/17/18.
 */

public class ReportProblemPresenter {
    private final ApiService apiService;

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
     * @param reportType - the type of return
     *
     */
    public void reportProblem(String reportedBy, String message, String reportType) {
        apiService.reportProblem(reportedBy, message, reportType).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ReportProblem -> reportProblemSuccess(),
                        e -> reportProblemError(e)
                );
    }

    /**
     * Success on call of an endpoint.
     */
    public void reportProblemSuccess() {
        Log.d("Api Debugging", "Done calling the Api successfully ");
    }

    /**
     * Show error resulted from call of the endpoint.
     *
     * @param e throwable exception
     */
    public void reportProblemError(Throwable e) {
        Log.i("Error Report Problem", e.toString());
    }
}
