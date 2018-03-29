package com.andela.art.login;

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
}
