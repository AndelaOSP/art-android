package com.andela.art.root;

import android.app.Application;
import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by Mugiwara_Munyi on 28/02/2018.
 */

@Module
public class ApplicationModule {

  private Application application;
  public ApplicationModule (Application application){
    this.application = application;

  }

  @Provides
  @Singleton
  public Context provideContext(){
    return application;
  }
}
