package com.andela.art.serialentry.domain;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by zack on 3/5/18.
 */

public abstract class UseCase<T> {

    public abstract Single<T> buildObservable();

    public Single<T> execute(){
        return buildObservable();
    }
}
