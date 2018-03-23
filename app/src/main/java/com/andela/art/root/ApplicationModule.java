package com.andela.art.root;

import android.app.Application;
import android.content.Context;

import com.andela.art.utils.Constants;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

/**
 * Created by Mugiwara_Munyi on 28/02/2018.
 */

@Module
public class ApplicationModule {

  private final Application application;

  /**
   * Set the application.
   *
   * @param application - appliation
   */
  public ApplicationModule(Application application) {
    this.application = application;

  }

  /**
   * Provide GsonConverterFactory.
   * @return GsonConverterFactory
   */
  @Singleton
  @Provides
  GsonConverterFactory gsonConverterFactory() {
    return GsonConverterFactory.create();
  }

  /**
   * Provide HttpLoggingInterceptor.
   * @return HttpLoggingInterceptor
   */
  @Singleton
  @Provides
  HttpLoggingInterceptor provideHttpLoggingInterceptor() {
    return new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
  }

  /**
   * Provide OkHttpClient.
   * @param loggingInterceptor - logging interceptor
   * @return OkHttpClient
   */
  @Singleton
  @Provides
  OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
    return new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build();
  }

  /**
   * Provide RxJava2CallAdapterFactory.
   * @return RxJava2CallAdapterFactory
   */
  @Singleton
  @Provides
  RxJava2CallAdapterFactory provideRxJavaCallAdapterFactory() {
    return  RxJava2CallAdapterFactory.create();
  }

  /**
   * Provide Retrofit.
   * @param client - OkHttpClient
   * @param converterFactory - GsonConverterFactory
   * @param rxJavaCallAdapterFactory - RxJava2CallAdapterFactory
   * @return Retrofit
   */
  @Singleton
  @Provides
  Retrofit provideRetrofit(OkHttpClient client, GsonConverterFactory converterFactory,
                           RxJava2CallAdapterFactory rxJavaCallAdapterFactory) {
    return new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(rxJavaCallAdapterFactory)
            .client(client)
            .build();
  }

  /**
   * Provide the application context.
   *
   * @return application
   */
  @Provides
  @Singleton
  public Context provideContext() {
    return application;
  }
}
