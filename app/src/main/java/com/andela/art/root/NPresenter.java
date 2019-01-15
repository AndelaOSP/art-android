package com.andela.art.root;

/**
 * NfcPresenter interface.
 * @param <U>
 */

public interface NPresenter<U extends View> {
    /**
     * @param view view that will be instantiated by the presenter
     */
    void attachVieww(U view);
}
