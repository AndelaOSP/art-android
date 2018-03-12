package com.andela.art.checkin;

import com.andela.art.checkin.data.CheckInService;
import com.andela.art.checkin.models.CheckinResponse;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


class CheckInPresenter implements Observer<CheckinResponse> {

    @Inject CheckInView view;
    @Inject CheckInService mCheckInService;

    @Inject
    public CheckInPresenter(CheckInView view) {
        this.view = view;
    }

    private <T> void subscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * Calls API service to hit check in endpoint.
     */
    protected void checkIn(String serialNumber) {
        Observable<CheckinResponse> checkin = mCheckInService.checkIn(serialNumber);
        subscribe(checkin, this);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(CheckinResponse checkinResponse) {
        view.showCheckout();
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
