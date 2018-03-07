package com.andela.art.serialentry.presentation;

import android.util.Log;

import com.andela.art.common.Presenter;
import com.andela.art.serialentry.data.Asset;
import com.andela.art.serialentry.domain.GetAssetUseCase;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zack on 3/5/18.
 */

public class SerialPresenter implements Presenter<SerialView>{
    private GetAssetUseCase getAssetUseCase;
    private Disposable disposable;
    private SerialView serialView;

    public SerialPresenter(GetAssetUseCase getAssetUseCase) {
        this.getAssetUseCase = getAssetUseCase;
    }

    public void getAsset(String serial){
        getAssetUseCase.setSerial(serial);
        getAssetUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }


    @Override
    public void attachView(SerialView view) {
        this.serialView = view;
    }

}
