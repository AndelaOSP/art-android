package com.andela.art.serialentry.presentation;

import com.andela.art.api.ApiService;
import com.andela.art.common.Presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zack on 3/5/18.
 */


public class SerialPresenter implements Presenter<SerialView> {
    private SerialView serialView;
    private final ApiService apiService;
    private Disposable disposable;

    /**
     * Serial presenter constructor.
     * @param apiService api service interface
     */
    public SerialPresenter(ApiService apiService) {
        this.apiService = apiService;
    }

    /**
     * Return asset from disposable.
     * @param serial serial entered by dialog
     */
    public void getAsset(String serial) {
         disposable = apiService.getAsset(serial).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(serialView::sendIntent);
    }

    /**
     * Instantiate view that will be used by the presenter.
     * @param view view that will be instantiated
     */
    @Override
    public void attachView(SerialView view) {
        this.serialView = view;
    }

}
