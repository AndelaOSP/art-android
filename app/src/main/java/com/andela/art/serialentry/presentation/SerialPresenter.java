package com.andela.art.serialentry.presentation;

import android.util.Log;

import com.andela.art.api.ApiService;
import com.andela.art.common.Presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zack on 3/5/18.
 */

public class SerialPresenter implements Presenter<SerialView> {
    private Disposable disposable;
    private SerialView serialView;
    private ApiService apiService;

    public SerialPresenter(ApiService apiService) {
        this.apiService = apiService;
    }


    public void getAsset(String serial) {
        disposable = apiService.getAsset(serial).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(serialView::sendIntent);
    }
    @Override
    public void attachView(SerialView view) {
        this.serialView = view;
    }

}
