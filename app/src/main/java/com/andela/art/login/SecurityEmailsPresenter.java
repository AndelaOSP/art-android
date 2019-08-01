package com.andela.art.login;

import android.content.Context;
import android.widget.Toast;

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
    private final Context mContext;

    /**
     * @param sharedPrefsWrapper  - sharedPrefsWrapper
     * @param apiService - apiService
     * @param context - context
     */
    public SecurityEmailsPresenter(SharedPrefsWrapper sharedPrefsWrapper, ApiService apiService,
                                   Context context) {
        this.apiService = apiService;
        this.sharedPrefsWrapper = sharedPrefsWrapper;
        this.mContext = context;
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
                }, e -> Toast
                        .makeText(mContext, "No internet Connection", Toast.LENGTH_SHORT)
                        .show());
    }
    /**
     * Fetch the emails for a security user.
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
