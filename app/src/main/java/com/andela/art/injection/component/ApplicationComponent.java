package com.andela.art.injection.component;

import com.andela.art.injection.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;


@Component(modules = ApplicationModule.class)
@Singleton
public interface ApplicationComponent {

    Retrofit exposeRetrofit();
}
