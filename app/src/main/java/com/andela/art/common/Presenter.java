package com.andela.art.common;


/**
 * Created by zack on 3/6/18.
 */

public interface Presenter<T extends View> {
    void attachView(T view);
}
