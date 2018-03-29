package com.andela.art.serialentry.presentation;

import com.andela.art.common.View;
import com.andela.art.serialentry.data.Asset;

/**
 * Created by zack on 3/5/18.
 */

public interface SerialView extends View {
    /**
     *
     * @param serial serial entered by dialog
     */
    void onConfirmClicked(String serial);

    /**
     *
     * @param asset asset that is passed from presenter
     */
    void sendIntent(Asset asset);
}
