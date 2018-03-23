package com.andela.art.checkin;

import com.andela.art.api.CheckInService;
import com.andela.art.model.CheckInResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Check in Presenter class.
 */
public class CheckInPresenterImpl implements CheckInPresenter {
    private final CheckInView view;
    private final CheckInService checkInService;

    /**
     * Check in presenter constructor.
     * @param view - check in view
     * @param checkInService - check in service.
     */
    public CheckInPresenterImpl(CheckInView view, CheckInService checkInService) {
        this.view = view;
        this.checkInService = checkInService;
    }

    /**
     * Check in user with asset serial number.
     * @param serial - asset serial number.
     */
    public void checkIn(String serial) {
        Observable<CheckInResponse> checkin = checkInService.checkIn(serial);
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
     * Call method to show checkout button.
     */
    public void callShowCheckOut() {
        view.showCheckout();
    }
}
