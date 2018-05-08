package com.andela.art.userdashboard.presentation;


import com.andela.art.models.Asset;
import com.andela.art.root.View;

import java.util.List;

/**
 * Andela User dashboard View.
 */
public interface UserDashBoardView extends View {

    /**
     * Load image to the image view from intent url.
     * @param url - image url
     */
    void onLoadResizedImage(String url);

    /**
     *Handle error message.
     * @param error error.
     */
    void onDisplayErrorMessage(Throwable error);

    /**
     * gets all the assets for a particular user.
     *
     * @param assets List of assets
     */
    void onGetAssets(List<Asset> assets);
}
