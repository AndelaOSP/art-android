package com.andela.art.login;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mugiwara_Munyi on 28/02/2018.
 */

@Module
public class LoginModule {

    @Provides
    public LoginActivityMVP.LoginPresenter provideLoginActivityPresenter(
            LoginModel model) {
        return new LoginPresenter(model);
    }

    @Provides
    public LoginActivityMVP.LoginModel provideLoginActivityModel(LoginRepository repository) {
        return new LoginModel(repository);
    }

    @Provides
    public LoginRepository provideLoginRepository() {
        return new LoginRepository();
    }
}
