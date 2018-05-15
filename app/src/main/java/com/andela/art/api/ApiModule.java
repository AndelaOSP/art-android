package com.andela.art.api;

import com.andela.art.BuildConfig;
import com.andela.art.root.SharedPrefsWrapper;


import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zack on 3/23/18.
 */

@Module()
public class ApiModule {

    /**
     * Provide client instance for injection.
     * @param sharedPrefsWrapper - shared prefs wrapper
     * @return OkHttp client instance
     */
    @Provides
    OkHttpClient provideOkHttp(SharedPrefsWrapper sharedPrefsWrapper) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor authInterceptor = chain -> {
            Request request = chain.request();
            if (request.url().encodedPath().equalsIgnoreCase("/api/v1/o/token/")
                    || request.url().encodedPath().
                    equalsIgnoreCase("/api/v1/security-user-emails/")) {
                HttpUrl url = request.url();
                HttpUrl newUrl = url.newBuilder()
                        .build();
                Request.Builder builder = request.newBuilder()
                        .url(newUrl)
                        .addHeader("Authorization",
                        "Bearer " + sharedPrefsWrapper.getString("OAUTH"));
                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
            HttpUrl url = request.url();
            HttpUrl newUrl = url.newBuilder()
                    .build();
            Request.Builder builder = request.newBuilder()
                    .url(newUrl)
                    .addHeader("Authorization",
                            "Token " + sharedPrefsWrapper.getString("AUTH_TOKEN"));
            Request newRequest = builder.build();
            return chain.proceed(newRequest);

        };
        return new OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();
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
     * @return ApiService instance
     */
    @Provides
    ApiService provideService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
