package com.andela.art.userdashboard.presentation;

import com.andela.art.api.UserAssetResponse;
import com.andela.art.root.View;

/**
 * Created by zack on 5/11/18.
 */

public interface SliderView extends View {
    /**
     *Handle error message.
     * @param error error.
     */
    void onDisplayErrorMessage(Throwable error);

    /**
     * gets all the assets for a particular user.
     *
     * @param response List of assets
     */
    void onGetAssets(UserAssetResponse response);
}
