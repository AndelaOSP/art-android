package com.andela.art.root;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by Mugiwara_Munyi on 28/02/2018.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

  Retrofit exposeRetrofit();
  Context exposeContext();

}

