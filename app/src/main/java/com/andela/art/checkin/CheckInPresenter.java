package com.andela.art.checkin;


import com.andela.art.api.ApiService;
import com.andela.art.models.CheckInModel;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Check in presenter.
 */
public class CheckInPresenter {
    protected CheckInView view;
    private final ApiService apiService;

    /**
     * Check in presenter constructor.
     * @param apiService - check in service.
     */
    public CheckInPresenter(ApiService apiService) {
        this.apiService = apiService;
    }

    /**
     * Check in user with asset serial number.
     * @param id - asset serial number.
     * @param logType - checkin status
     */
    public void checkIn(Integer id, String logType) {
        Observable<CheckInModel> checkin = apiService.checkIn(id, logType);
        checkin.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<CheckInModel>() {
                    @Override
                    public void onNext(CheckInModel response) {
                        view.goToCheckSerial();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.displayError(logType);
                    }

                    @Override
                    public void onComplete() {
                        // Run this after completion.
                    }
                });
    }

    /**
     * Attach view to presenter.
     * @param checkInView - CheckInView
     */
    public void attachView(CheckInView checkInView) {
        this.view = checkInView;
    }

}
