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
     * @param serial - asset serial number.
     * @param logType - checkin status
     */
    public void checkIn(String serial, String logType) {
        Observable<CheckInModel> checkin = apiService.checkIn(serial, logType);
        checkin.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<CheckInModel>() {
                    @Override
                    public void onNext(CheckInModel response) {
                        view.goToCheckSerial();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Run this in case of errors.
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
