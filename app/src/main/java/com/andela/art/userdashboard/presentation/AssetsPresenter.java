package com.andela.art.userdashboard.presentation;

import com.andela.art.api.ApiService;
import com.andela.art.root.Presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lewismbogo on 08/05/2018.
 */

public class AssetsPresenter implements Presenter<UserDashBoardView> {
    public UserDashBoardView userDashBoardView;
    public ApiService apiService;
    private Disposable disposable;

    /**
     * Serial presenter constructor.
     * @param apiService api service interface
     */
    public AssetsPresenter(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void attachView(UserDashBoardView view) {
        this.userDashBoardView = view;
    }

    /**
     * get Assets for a particular user.
     */
    public void getAssets() {
        disposable = apiService.getAssets().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(userDashBoardView::onGetAssets,
                        userDashBoardView::onDisplayErrorMessage);
    }
}
