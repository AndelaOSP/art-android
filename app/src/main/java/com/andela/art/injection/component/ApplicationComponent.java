package com.andela.art.injection.component;

import android.content.Context;

import com.andela.art.injection.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Retrofit exposeRetrofit();
    Context exposeContext();
}
