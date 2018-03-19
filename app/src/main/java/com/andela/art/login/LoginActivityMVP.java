package com.andela.art.login;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by Mugiwara_Munyi on 28/02/2018.
 */

public interface LoginActivityMVP {

    /**
     * LoginActivity interface handles authentication.
     */
    interface LoginActivity {

        /**
         * firebasewithGoogleAuth  handles Google authentication.
         *
         * @param account  account - Google account.
         */
        void firebasewithGoogleAuth(GoogleSignInAccount account);

        /**
         * signIn handles authentication.
         */
        void signIn();

    }

    /**
     * LoginPresenter - Presenter.
     */
    interface LoginPresenter {
        //Silent

    }

    /**
     * LoginModel - Model.
     */
    interface LoginModel {

    }
}
