package com.andela.art.checkin;

import com.andela.art.api.ApiService;
import com.andela.art.checkin.data.CheckInModel;
import com.andela.art.checkin.data.CheckInResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Check in presenter.
 */
public class CheckInPresenter {
    private CheckInView view;
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
     * @param securityUser - security user name.
     */
    public void checkIn(String serial, String securityUser) {
        CheckInModel checkInModel = new CheckInModel();
        checkInModel.setAction("CHECKIN");
        checkInModel.setSecurityUser(securityUser);
        checkInModel.setSerialNumber(serial);
        Observable<CheckInResponse> checkin = apiService.checkIn(checkInModel);
        checkin.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<CheckInResponse>() {
                    @Override
                    public void onNext(CheckInResponse response) {
                        callShowCheckOut();
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

    /**
     * Call method to show checkout button.
     */
    public void callShowCheckOut() {
        view.showCheckout();
    }
}
