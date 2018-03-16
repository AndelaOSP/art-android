package com.andela.art.login;

import android.app.ProgressDialog;
import android.support.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

/**
 * Created by Mugiwara_Munyi on 28/02/2018.
 */

public class LoginPresenter implements LoginActivityMVP.LoginPresenter {

    @Nullable
    LoginModel model;
    GoogleSignInClient mGoogleSignInClient;
    ProgressDialog mConnectionProgressDialog;
    private static final int RC_SIGN_IN = 2;

    /**
     * LoginPresenter.
     *
     * @param model - LoginModel
     */
    public LoginPresenter(LoginModel model) {
        this.model = model;
    }

}
