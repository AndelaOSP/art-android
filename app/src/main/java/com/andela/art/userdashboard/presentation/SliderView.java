package com.andela.art.userdashboard.presentation;

import com.andela.art.models.Asset;
import com.andela.art.root.View;

import java.util.List;

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
     * @param assets List of assets
     */
    void onGetAssets(List<Asset> assets);
}
