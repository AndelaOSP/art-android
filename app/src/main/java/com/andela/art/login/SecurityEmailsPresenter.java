package com.andela.art.login;

import com.andela.art.api.ApiService;
import com.andela.art.root.Constants;
import com.andela.art.root.Presenter;
import com.andela.art.root.SharedPrefsWrapper;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zack on 4/16/18.
 */
public class SecurityEmailsPresenter implements Presenter<SecurityEmailsView> {

    private final ApiService apiService;
    private SecurityEmailsView securityEmailsView;
    SharedPrefsWrapper sharedPrefsWrapper;

    /**
     * @param sharedPrefsWrapper  - sharedPrefsWrapper
     * @param apiService - apiService
     */
    public SecurityEmailsPresenter(SharedPrefsWrapper sharedPrefsWrapper, ApiService apiService) {
        this.apiService = apiService;
        this.sharedPrefsWrapper = sharedPrefsWrapper;
    }

    /**
     * @param view view that will be instantiated by the presenter
     */
    @Override
    public void attachView(SecurityEmailsView view) {
        this.securityEmailsView = view;
    }

    /**
     * Retreive Oauth Token.
     */
    public void retrieveOauthToken() {
        apiService.fetchOauthToken(Constants.GRANT_TYPE,
                Constants.CLIENT_ID,
                Constants.CLIENT_SECRET
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tokenResponse -> {
                    String accessToken = tokenResponse.getAccessToken();
                    sharedPrefsWrapper.putString("OAUTH", accessToken);
                    fetchSecurityUserEmails();
                });

    }
    /**
     * Fetch the emails for a security user.
     * @param
     */
    public void fetchSecurityUserEmails() {
         apiService.getEmails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(emailsResponse -> {
                    List<String> emails = emailsResponse.getEmails();
                    securityEmailsView.populateEmailList(emails);
                });
    }
}
