package com.andela.art.login;

import android.content.Context;

import com.andela.art.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mugiwara_Munyi on 28/02/2018.
 */

@Module
public class LoginModule {

    /**
     * Provide the loginPresenter.
     *
     * @param model - LoginModel
     * @return LoginPresenter class
     */
    @Provides
    public LoginActivityMVP.LoginPresenter provideLoginActivityPresenter(
            LoginModel model) {
        return new LoginPresenter(model);
    }

    /**
     * Provide loginModel.
     *
     * @param repository - LoginRepository
     * @return LoginModel class
     */
    @Provides
    public LoginActivityMVP.LoginModel provideLoginActivityModel(LoginRepository repository) {
        return new LoginModel(repository);
    }

    /**
     * Provide LoginRepository.
     *
     * @return LoginRepository class
     */
    @Provides
    public LoginRepository provideLoginRepository() {
        return new LoginRepository();
    }

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
}
