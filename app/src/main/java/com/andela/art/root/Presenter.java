package com.andela.art.root;


/**
 * Presenter interface.
 * @param <T>
 */

public interface Presenter<T extends View> {
    /**
     * @param view view that will be instantiated by the presenter
     */
    void attachView(T view);
}
