package com.andela.art.login;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by Mugiwara_Munyi on 28/02/2018.
 */

public interface LoginActivityMVP {

    interface LoginActivity {

        void firebasewithGoogleAuth(GoogleSignInAccount account);

        void signIn();

    }

    interface LoginPresenter {
        //Silent

    }

    interface LoginModel {

    }
}
