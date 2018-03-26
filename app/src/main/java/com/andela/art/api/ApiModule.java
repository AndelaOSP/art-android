package com.andela.art.api;

import com.andela.art.util.Constants;

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

    @Provides
    OkHttpClient provideOkHttp(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor()).build();
    }

    @Provides
    Retrofit provideRetrofit(OkHttpClient client){
        return new Retrofit
                .Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    ApiService provideService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }
}
