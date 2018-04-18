package com.andela.art.login.injection;

import android.content.Context;

import com.andela.art.R;
import com.andela.art.api.ApiService;
import com.andela.art.login.SecurityEmailsPresenter;
import com.andela.art.login.TokenAuthPresenter;
import com.andela.art.root.SharedPrefsWrapper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mugiwara_Munyi on 28/02/2018.
 */

@Module
public class LoginModule {
    /**
     * Provides GoogleSignInClient.
     *
     * @param context - Context
     * @return GoogleSignInClient object
     */
    @Provides
    public GoogleSignInClient providesGoogleSignInClient(Context context) {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();
        return GoogleSignIn.getClient(context, googleSignInOptions);
    }

    /**
     * Provide SecurityEmailsPresenter.
     * @param apiService - apiService
     * @return SecurityEmailsPresenter object
     */
    @Provides
    public SecurityEmailsPresenter providesSecurityEmailsPresenter(ApiService apiService) {
        return new SecurityEmailsPresenter(apiService);
    }

    /**
     * Provide TokenAuthPresenter.
     * @param firebaseAuth - firebaseAuth
     * @param sharedPrefsWrapper - sharedPrefsWrapper
     * @return TokenAuthPresenter object
     */
    @Provides
    public TokenAuthPresenter providesTokenAuthPresenter(FirebaseAuth firebaseAuth,
                                                         SharedPrefsWrapper sharedPrefsWrapper) {
        return new TokenAuthPresenter(firebaseAuth, sharedPrefsWrapper);
    }
}
