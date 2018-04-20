package com.andela.art.feedback.service;

import com.andela.art.BuildConfig;
import com.andela.art.api.ApiService;
import com.andela.art.models.Feedback;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by allan on 17/04/2018.
 */

public class PostFeedback {

    private final ApiService apiService;

    /**
     * No arg constructor for the class.
     */
    public PostFeedback() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    /**
     * Method to call the API and send the Feedback message.
     *
     * @param feedback the Feedback object to be sent
     * @return Disposable
     */
    public Observable<Feedback> sendFeedbackToAPI(Feedback feedback) {
        return apiService.postFeedback(feedback).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
