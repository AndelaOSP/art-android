package com.andela.art.root;

import com.andela.art.login.LoginActivity;
import com.andela.art.login.LoginModule;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by Mugiwara_Munyi on 28/02/2018.
 */

@Singleton
@Component(modules = {ApplicationModule.class, LoginModule.class})
public interface ApplicationComponent {

  void inject(LoginActivity target);

}

