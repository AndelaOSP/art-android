package com.andela.art.securitydashboard.presentation;

import com.andela.art.api.ApiService;
import com.andela.art.root.Presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kalela on 20/11/18.
 */

public class NfcPresenter implements Presenter<NfcView> {
    private NfcView nfcView;
    private final ApiService apiService;
    private Disposable disposable;

    /**
     * Serial presenter constructor.
     * @param apiService api service interface
     */
    public NfcPresenter(ApiService apiService) {
        this.apiService = apiService;
    }

    /**
     * Return asset from disposable.
     * @param serial serial entered by dialog
     */
    public void getAsset(String serial) {
        disposable = apiService.getAsset(serial).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(nfcView::sendIntent,
                        nfcView::displayErrorMessage);
    }

    /**
     * Instantiate view that will be used by the presenter.
     * @param view view that will be instantiated
     */
    @Override
    public void attachView(NfcView view) {
        this.nfcView = view;
    }

    /**
     * Dispose disposable after activity stops.
     */
    public void dispose() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }

    }
}
