package com.andela.art.root;

import com.andela.art.checkin.CheckInActivity;
import com.andela.art.di.CheckInModule;
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

  /**
   * Inject the login activity.
   *
   * @param target - The target activity
   */
  void inject(LoginActivity target);

}

