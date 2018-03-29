package com.andela.art.api;

import com.andela.art.BuildConfig;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zack on 3/23/18.
 */

@Module
public class ApiModule {


    /**
     * Provide client instance for injection.
     * @return OkHttp client instance
     */
    @Provides
    OkHttpClient provideOkHttp() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor()).build();
    }


    /**
     * Provide retrofit instance for injection.
     *
     * @param client Okhttp client to be used
     * @return Retrofit instances
     */
    @Provides
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit
                .Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Provide api service interface for injection.
     *
     * @param retrofit retrofit instance used by api service
     *
     * @return ApiService instance
     */
    @Provides
    ApiService provideService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
