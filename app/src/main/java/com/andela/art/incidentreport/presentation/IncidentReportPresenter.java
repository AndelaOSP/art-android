package com.andela.art.incidentreport.presentation;

import com.andela.art.api.ApiService;
import com.andela.art.models.IncidentModel;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Incident report presenter.
 */
public class IncidentReportPresenter {
    protected IncidentReportView view;

    private final ApiService apiService;

    /**
     * Incident report presenter constructor.
     * @param apiService - api service instance
     */
    public  IncidentReportPresenter(ApiService apiService) {
        this.apiService = apiService;
    }

    /**
     * Call the api service.
     * @param model - incident model
     */
    public void reportIncident(IncidentModel model) {
        Observable<IncidentModel> incident = apiService.reportIncident(model);
        incident.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<IncidentModel>() {
                    @Override
                    public void onNext(IncidentModel response) {
                        // Called before OnComplete
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Run this in case of errors.
                    }

                    @Override
                    public void onComplete() {
                        // Run this on completion.
                        view.showSuccess();

                    }
                });
    }

    /**
     * Attach view to presenter.
     * @param incidentReportView - incident report view
     */
    public void attachView(IncidentReportView incidentReportView) {
        this.view = incidentReportView;
    }

}
