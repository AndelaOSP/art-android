package com.andela.art.checkin;

import com.andela.art.data.CheckInService;
import com.andela.art.data.ServiceBuilder;
import com.andela.art.models.CheckinResponse;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


class CheckInPresenter implements Observer<CheckinResponse> {

    @Inject CheckInView view;

    @Inject
    public CheckInPresenter() {

    }

    private <T> void subscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * Calls API service to hit check in endpoint.
     */
    public void checkIn(String serialNumber) {
        CheckInService mCheckInService = ServiceBuilder.buildService(CheckInService.class);
        Observable<CheckinResponse> checkin = mCheckInService.checkIn(serialNumber);
        subscribe(checkin, this);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(CheckinResponse checkinResponse) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
