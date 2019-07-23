package com.andela.art.root;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mugiwara_Munyi on 28/02/2018.
 */

@Module
public class ApplicationModule {

  private final Application application;

  /**
   * Set the application.
   *
   * @param application - application
   */
  public ApplicationModule(Application application) {
    this.application = application;

  }

  /**
   * Provide the application context.
   *
   * @return application
   */
  @Provides
  public Context provideContext() {
    return application;
  }

  /**
   * Provide SharedPreferences implementation.
   * @return SharedPreferenceImpl - object
   */
  @Provides
  public SharedPreferenceImpl provideSharedPreference() {
    return new SharedPreferenceImpl(application);
  }

  /**
   *
   * @param sharedPreference - sharedPreference
   * @return sharedPreference
   */
  @Provides
  public SharedPrefsWrapper provideSharedPrefsWrapper(SharedPreferenceImpl sharedPreference) {
    return sharedPreference;
  }
}
